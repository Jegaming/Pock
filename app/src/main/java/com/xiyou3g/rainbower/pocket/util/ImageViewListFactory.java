package com.xiyou3g.rainbower.pocket.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.xiyou3g.rainbower.pocket.model.HomePagerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell2014 on 2017/5/19.
 */

public class ImageViewListFactory {

    public static List<ImageView> convertImageViewListByImageView(Context context, List<ImageView> imageViewList) {
        List<ImageView> newImageViewList = new ArrayList<>();
        Drawable drawable;

        drawable = imageViewList.get(0).getDrawable();
        ImageView firstImageView = new ImageView(context);
        firstImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        firstImageView.setImageDrawable(drawable);

        drawable = imageViewList.get(imageViewList.size() - 1).getDrawable();
        ImageView lastImageView = new ImageView(context);
        lastImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        lastImageView.setImageDrawable(drawable);

        for (int i = 0; i < imageViewList.size() + 2; i++) {
            if (i == 0) {
                newImageViewList.add(lastImageView);
                continue;
            }
            if (i == imageViewList.size() + 1) {
                newImageViewList.add(firstImageView);
                continue;
            }
            newImageViewList.add(imageViewList.get(i - 1));
        }
        return newImageViewList;
    }

    public static List<ImageView> convertImageViewListByItem(Context context, List<HomePagerItem> pagerItemList) {
        List<ImageView> newImageViewList = new ArrayList<>();

        for (int i = 0; i < pagerItemList.size() + 2; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (i == 0) {
                imageView.setImageBitmap(pagerItemList.get(pagerItemList.size() - 1).getPic());
                newImageViewList.add(imageView);
                continue;
            }
            if (i == pagerItemList.size() + 1) {
                imageView.setImageBitmap(pagerItemList.get(0).getPic());
                newImageViewList.add(imageView);
                continue;
            }
            imageView.setImageBitmap(pagerItemList.get(i - 1).getPic());
            newImageViewList.add(imageView);
        }
        return newImageViewList;
    }

}
