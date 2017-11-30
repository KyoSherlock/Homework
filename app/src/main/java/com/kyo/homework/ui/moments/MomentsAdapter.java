package com.kyo.homework.ui.moments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kyo.homework.R;
import com.kyo.homework.data.CommentEntity;
import com.kyo.homework.data.ImageEntity;
import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.SenderEntity;
import com.kyo.homework.data.UserEntity;
import com.kyo.homework.util.ContextUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jianghui on 2017/11/29.
 */

public class MomentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_INVALID = 0;
    private static final int TYPE_0_PIC = 1;
    private static final int TYPE_1_PIC = 2;
    private static final int TYPE_3_PIC = 3;
    private static final int TYPE_4_PIC = 4;
    private static final int TYPE_6_PIC = 5;
    private static final int TYPE_9_PIC = 6;
    private static final int TYPE_HEAD = 12;


    private final List<Item> items = new LinkedList<>();
    private final LayoutInflater layoutInflater;
    private final MomentsActivity activity;
    private int itemImageWidth;

    public MomentsAdapter(MomentsActivity activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
        int screenWidth = this.activity.getResources().getDisplayMetrics().widthPixels;
        itemImageWidth = (screenWidth - ContextUtil.dp2px(this.activity, 100)) / 3;
        // --
        HeadItem item = new HeadItem();
        item.type = TYPE_HEAD;
        items.add(item);
    }

    public void setUserInfo(UserEntity userEntity) {
        HeadItem item = new HeadItem();
        item.userEntity = userEntity;
        item.type = TYPE_HEAD;
        items.set(0, item);
    }


    public void setMoments(@NonNull List<MomentEntity> momentEntities) {
        Item headItem = items.get(0);
        items.clear();
        items.add(headItem);
        addMoments(momentEntities);
    }

    public void addMoments(@NonNull List<MomentEntity> momentEntities) {
        for (MomentEntity entity : momentEntities) {
            if (entity.sender != null) {

                MomentItem item = new MomentItem();
                if (entity.images != null && !entity.images.isEmpty()) {
                    if (entity.images.size() > 6) {
                        item.type = TYPE_9_PIC;
                    } else if (entity.images.size() > 4) {
                        item.type = TYPE_6_PIC;
                    } else if (entity.images.size() > 3) {
                        item.type = TYPE_4_PIC;
                    } else if (entity.images.size() > 1) {
                        item.type = TYPE_3_PIC;
                    } else {
                        item.type = TYPE_1_PIC;
                    }
                } else {
                    item.type = TYPE_0_PIC;
                }
                if (item.type == TYPE_0_PIC && TextUtils.isEmpty(entity.content)) {
                    continue;
                }
                item.momentEntity = entity;
                items.add(item);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_HEAD) {
            View itemView = layoutInflater.inflate(R.layout.item_moment_head, parent, false);
            holder = new HeadHolder(itemView);
        } else if (viewType == TYPE_0_PIC) {
            View itemView = layoutInflater.inflate(R.layout.item_moment_0_pic, parent, false);
            holder = new ContentHolder(itemView);
        } else if (viewType == TYPE_1_PIC) {
            View itemView = layoutInflater.inflate(R.layout.item_moment_1_pic, parent, false);
            holder = new ImageHolder(itemView, 1, itemImageWidth);
        } else if (viewType == TYPE_3_PIC) {
            View itemView = layoutInflater.inflate(R.layout.item_moment_3_pic, parent, false);
            holder = new ImageHolder(itemView, 3, itemImageWidth);
        } else if (viewType == TYPE_4_PIC) {
            View itemView = layoutInflater.inflate(R.layout.item_moment_4_pic, parent, false);
            holder = new ImageHolder(itemView, 4, itemImageWidth);
        } else if (viewType == TYPE_6_PIC) {
            View itemView = layoutInflater.inflate(R.layout.item_moment_6_pic, parent, false);
            holder = new ImageHolder(itemView, 6, itemImageWidth);
        } else if (viewType == TYPE_9_PIC) {
            View itemView = layoutInflater.inflate(R.layout.item_moment_9_pic, parent, false);
            holder = new ImageHolder(itemView, 9, itemImageWidth);
        } else {
            View itemView = new View(parent.getContext());
            holder = new EmptyHolder(itemView);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);
        final int type = item.type;
        switch (type) {
            case TYPE_HEAD: {
                HeadItem headItem = (HeadItem) item;
                HeadHolder headHolder = (HeadHolder) holder;
                onBindHeadHolder(headItem, headHolder);
                break;
            }
            case TYPE_0_PIC: {
                MomentItem momentItem = (MomentItem) item;
                ContentHolder contentHolder = (ContentHolder) holder;
                onBindContentHolder(momentItem, contentHolder);
                break;
            }
            case TYPE_1_PIC:
            case TYPE_3_PIC:
            case TYPE_4_PIC:
            case TYPE_6_PIC:
            case TYPE_9_PIC: {
                MomentItem momentItem = (MomentItem) item;
                ImageHolder imageHolder = (ImageHolder) holder;
                onBindImageHolder(momentItem, imageHolder);
                break;
            }
            default:
                break;
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).type;
    }


    private void onBindHeadHolder(HeadItem headItem, HeadHolder headHolder) {
        UserEntity userEntity = headItem.userEntity;
        if (userEntity != null) {
            Glide.with(activity).load(headItem.userEntity.profileImage).into(headHolder.profileImage);
            Glide.with(activity).load(headItem.userEntity.avatar).into(headHolder.avatar);
            headHolder.nick.setText(headItem.userEntity.nick);
        }
    }

    private void onBindContentHolder(MomentItem momentItem, ContentHolder contentHolder) {
        MomentEntity entity = momentItem.momentEntity;
        // sender
        contentHolder.nick.setText(entity.sender.nick);
        // content
        if (!TextUtils.isEmpty(entity.content)) {
            contentHolder.content.setText(entity.content);
            contentHolder.content.setVisibility(View.VISIBLE);
        } else {
            contentHolder.content.setVisibility(View.GONE);
        }
        // avatar
        Glide.with(activity).load(entity.sender.avatar).into(contentHolder.avatar);
        // comments
        if (entity.comments != null) {
            StringBuilder stringBuilder = new StringBuilder();
            int j = 0;
            for (CommentEntity commentEntity : entity.comments) {
                SenderEntity senderEntity = commentEntity.sender;
                if (j == 0) {
                    stringBuilder.append(senderEntity.nick + ":" + commentEntity.content);
                } else {
                    stringBuilder.append("\n" + senderEntity.nick + ":" + commentEntity.content);
                }
                j++;
            }
            if (!TextUtils.isEmpty(stringBuilder)) {
                contentHolder.comment.setText(stringBuilder);
                contentHolder.comment.setVisibility(View.VISIBLE);
            } else {
                contentHolder.comment.setVisibility(View.GONE);
            }
        } else {
            contentHolder.comment.setVisibility(View.GONE);
        }
    }


    private void onBindImageHolder(MomentItem momentItem, ImageHolder imageHolder) {
        MomentEntity entity = momentItem.momentEntity;
        // sender
        imageHolder.nick.setText(entity.sender.nick);
        // content
        if (!TextUtils.isEmpty(entity.content)) {
            imageHolder.content.setText(entity.content);
            imageHolder.content.setVisibility(View.VISIBLE);
        } else {
            imageHolder.content.setVisibility(View.GONE);
        }
        // avatar
        Glide.with(activity).load(entity.sender.avatar).into(imageHolder.avatar);
        // comments
        if (entity.comments != null) {
            StringBuilder stringBuilder = new StringBuilder();
            int j = 0;
            for (CommentEntity commentEntity : entity.comments) {
                SenderEntity senderEntity = commentEntity.sender;
                if (j == 0) {
                    stringBuilder.append(senderEntity.nick + ":" + commentEntity.content);
                } else {
                    stringBuilder.append("\n" + senderEntity.nick + ":" + commentEntity.content);
                }
                j++;
            }
            if (!TextUtils.isEmpty(stringBuilder)) {
                imageHolder.comment.setText(stringBuilder);
                imageHolder.comment.setVisibility(View.VISIBLE);
            } else {
                imageHolder.comment.setVisibility(View.GONE);
            }
        } else {
            imageHolder.comment.setVisibility(View.GONE);
        }
        // images
        int a = 0;
        for (ImageView imageView : imageHolder.images) {
            if (imageView == null) {
                Log.e("Kyo", "image is null,i =" + a);
            } else {
                imageView.setVisibility(View.INVISIBLE);
            }
            a++;
        }
        int i = 0;
        for (ImageEntity imageEntity : entity.images) {
            imageHolder.images[i].setVisibility(View.VISIBLE);
            Glide.with(activity).load(imageEntity.url).into(imageHolder.images[i]);
            i++;
        }
    }

    private static class Item {
        int type;
    }

    private static class HeadItem extends Item {
        UserEntity userEntity;
    }

    private static class MomentItem extends Item {
        MomentEntity momentEntity;
    }

    private static class LoadMoreItem extends Item {

    }

    private static class HeadHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        ImageView avatar;
        TextView nick;

        public HeadHolder(View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            nick = (TextView) itemView.findViewById(R.id.nick);
        }
    }

    private static class OnePicHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView nick;
        private TextView content;
        private ImageView image;
        private TextView comment;

        public OnePicHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            nick = (TextView) itemView.findViewById(R.id.nick);
            content = (TextView) itemView.findViewById(R.id.content);
            image = (ImageView) itemView.findViewById(R.id.image);
            comment = (TextView) itemView.findViewById(R.id.comment);
        }
    }

    private static class ContentHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private TextView nick;
        private TextView content;
        private TextView comment;

        public ContentHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            nick = (TextView) itemView.findViewById(R.id.nick);
            content = (TextView) itemView.findViewById(R.id.content);
            comment = (TextView) itemView.findViewById(R.id.comment);
        }
    }

    private static class ImageHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private TextView nick;
        private TextView content;
        private ImageView[] images;
        private TextView comment;

        public ImageHolder(View itemView, int imageNum, int imageSize) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            nick = (TextView) itemView.findViewById(R.id.nick);
            content = (TextView) itemView.findViewById(R.id.content);
            comment = (TextView) itemView.findViewById(R.id.comment);
            if (imageNum > 0) {
                images = new ImageView[imageNum];
                for (int i = 0; i < imageNum; i++) {
                    switch (i) {
                        case 0:
                            images[i] = (ImageView) itemView.findViewById(R.id.image_1);
                            break;
                        case 1:
                            images[i] = (ImageView) itemView.findViewById(R.id.image_2);
                            break;
                        case 2:
                            images[i] = (ImageView) itemView.findViewById(R.id.image_3);
                            break;
                        case 3:
                            images[i] = (ImageView) itemView.findViewById(R.id.image_4);
                            break;
                        case 4:
                            images[i] = (ImageView) itemView.findViewById(R.id.image_5);
                            break;
                        case 5:
                            images[i] = (ImageView) itemView.findViewById(R.id.image_6);
                            break;
                        case 6:
                            images[i] = (ImageView) itemView.findViewById(R.id.image_7);
                            break;
                        case 7:
                            images[i] = (ImageView) itemView.findViewById(R.id.image_8);
                            break;
                        case 8:
                            images[i] = (ImageView) itemView.findViewById(R.id.image_9);
                            break;
                    }
                }

                if (imageNum == 1) {
                    images[0].getLayoutParams().width = imageSize * 3;
                    images[0].getLayoutParams().height = imageSize * 2;
                } else {
                    for (ImageView imageView : images) {
                        imageView.getLayoutParams().width = imageSize;
                        imageView.getLayoutParams().height = imageSize;
                    }
                }
            }
        }
    }

    private static class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
