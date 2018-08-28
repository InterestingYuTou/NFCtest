package com.yutou.ui.smallvideo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yutou.R;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * 描    述：选择本地视频界面
 * 创 建 人：ZJY
 * 创建日期：2017/6/21 17:03
 * 修订历史：
 * 修 改 人：
 */
public class SelectSmallVideoActivity extends Activity {
    private List<BaseBean> list = new ArrayList<>();
    private HashMap<Integer, String> map = new HashMap<>();

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_small_video);
        ButterKnife.bind(this);
        getPath();
        initData();
    }

    private void initData() {
        for (int i = 0; i < list.size(); i++) {
            map.put(i, "false");
        }
        final PhotoSelectAdapter adapter = new PhotoSelectAdapter(this);
        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerview.setAdapter(adapter);
    }

    /**
     * 获取视频列表
     */
    private void getPath() {
        String[] projections = {
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DURATION
        };
        String orderBys = MediaStore.Video.Media.DISPLAY_NAME;
        Uri uris = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        getVideoContentProvider(uris, projections, orderBys);
    }

    /**
     * 获取手机所有视频地址和视频时长
     */
    public void getVideoContentProvider(Uri uri, String[] projection, String orderBy) {
        // TODO Auto-generated method stub
        Cursor cursor = getContentResolver().query(uri, projection, null,
                null, orderBy);
        if (null == cursor) {
            return;
        }
        List<String> path = new ArrayList<>();
        List<String> time = new ArrayList<>();
        while (cursor.moveToNext()) {
            for (int i = 0; i < projection.length; i++) {
                if (i % 2 == 0) {
                    path.add(cursor.getString(i));
                } else {
                    time.add(cursor.getString(i) + "");
                }
            }
        }
        for (int i = 0; i < path.size(); i++) {
            File f = new File(path.get(i));
            String s = time.get(i);
            long l = Long.parseLong(s);
            int times = (int) l / 1000;
            if (times < 10 && times > 0) {
//                if (f.exists() && f.isFile() && f.length() < 2048000) {//文件大小过滤规则
                BaseBean bean = new BaseBean();
                bean.setIsvideo(true);
                bean.setUrl(path.get(i));
                bean.setVideotime("00:0" + times);
                bean.setVideosize(FormetFileSize(f.length()));
                list.add(bean);
//                }
            }
        }

    }

    class PhotoSelectAdapter extends RecyclerView.Adapter {
        private Context context;

        public PhotoSelectAdapter(Context context) {
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
            return new PhotoSelectVideoHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final BaseBean baseBean = list.get(position);
            PhotoSelectVideoHolder videoHolder = (PhotoSelectVideoHolder) holder;
            Glide.with(context).load(baseBean.getUrl()).into(videoHolder.video_iv);
            videoHolder.video_time.setText(baseBean.getVideotime());
            videoHolder.video_size.setText(baseBean.getVideosize());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("视频存储位置", "视频存储位置----->" + baseBean.getUrl());
//                    upLoadVideo(baseBean.getUrl());
                    startActivity(new Intent(context, VideoPlayerActivity.class).putExtra(
                            "path", baseBean.getUrl()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class PhotoSelectVideoHolder extends RecyclerView.ViewHolder {
            TextView video_time, video_size;
            ImageView video_iv;
            LinearLayout video_ll;

            public PhotoSelectVideoHolder(View itemView) {
                super(itemView);
                video_time = (TextView) itemView.findViewById(R.id.video_time);
                video_size = (TextView) itemView.findViewById(R.id.video_size);
                video_iv = (ImageView) itemView.findViewById(R.id.video_iv);
                video_ll = (LinearLayout) itemView.findViewById(R.id.video_ll);
            }
        }
    }

    private void upLoadVideo(String video_path) {
        File file = new File(video_path);
        String filename = file.getName();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"mFile\"; filename=\"" + filename + "\""), fileBody)
                .build();
        Request request = new Request.Builder()
                .url("")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e(TAG, "failure upload!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.i(TAG, "success upload!");
            }
        });
    }

    /**
     * 文件大小转换工具
     *
     * @param fileS
     * @return
     */
    public String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
