package com.bupt.affection.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bupt.affection.R;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.orhanobut.logger.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuyikun on 6/2/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<String> urlArrayList;
//    private ArrayList<String> objectIdArrayList;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static Map<Integer,String> objectIdMap;
    private ProgressDialog progressDialog;



    public RecyclerAdapter( ArrayList<String> urlArrayList, Context context) {
        this.urlArrayList = urlArrayList;
        this.context = context;
        objectIdMap = new HashMap<Integer,String>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView iv_preview;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_preview = (SimpleDraweeView) itemView.findViewById(R.id.iv_preview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_recyclerview, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Logger.e(urlArrayList.get(position));
        Uri uri = Uri.parse(urlArrayList.get(position));
//        objectIdMap.put(position, objectIdArrayList.get(position));
        holder.iv_preview.setTag(uri);
        holder.iv_preview.setImageURI(uri);
        holder.iv_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("key",v.getTag().toString());
                ImageRequest imageRequest = ImageRequest.fromUri(v.getTag().toString());
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                DataSource<CloseableReference<CloseableImage>> dataSource =
                        imagePipeline.fetchImageFromBitmapCache(imageRequest, null);
                dataSource.subscribe(new BaseBitmapDataSubscriber() {
                    @Override
                    public void onNewResultImpl(@Nullable Bitmap bitmap) {
                        if (bitmap != null) {
                            //由于返回的bitmap在方法执行完后就会回收,所以需要copy一份出来进行分享操作
                            shareIt(bitmap);
                        } else {
//                                sendTextMsgToWx(null, title, content, weburl, isToCircle);
                        }
                    }

                    @Override
                    public void onFailureImpl(DataSource dataSource) {
//                            sendTextMsgToWx(null, title, content, weburl, isToCircle);
                    }
                }, CallerThreadExecutor.getInstance());

            }

            private void shareIt(Bitmap bitmap) {
                File imageFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png");
                BufferedOutputStream bos = null;
                try {
                    bos = new BufferedOutputStream(new FileOutputStream(imageFile));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    bos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));
                context.startActivity(intent);

            }

        });
    }


    @Override
    public int getItemCount() {
        if (urlArrayList.size() > 0) {
            return urlArrayList.size();
        } else {
            return 0;
        }

    }

}
