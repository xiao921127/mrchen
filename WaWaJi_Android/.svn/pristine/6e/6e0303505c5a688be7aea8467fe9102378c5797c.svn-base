package com.wawaji.common.utils;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.wawaji.app.R;

/**
 * 在此写用途
 *
 * @author admin
 * @version V1.0 <描述当前版本功能>
 * @since 2017/11/13 14:10
 */

public class ImageSetting {

    public static void onImageSetting(Context context, String url, ImageView img){
        Picasso.with(context)
                .load(url)
                .fit().priority(Picasso.Priority.NORMAL)
//                    .tag(StringConfig.KEY_SUPER_TAG)
                .placeholder(R.drawable.ic_default_image)
                .error(R.drawable.ic_default_image)
                .centerInside()
                .into(img);
    }
}
