package com.getinfocia.infocia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.getinfocia.infocia.adapter.OtherAdapter;
import com.getinfocia.infocia.item.ItemOther;

import java.util.ArrayList;

public class OtherActivty extends Activity {

    OtherAdapter adapter;
    ArrayList<ItemOther> arrayOfOther;
    int img[] = {R.drawable.about, R.drawable.moreapp, R.drawable.feedback, R.drawable.rateapp};
    String name[] = {"About Us", "Feedback", "Terms and Conditions", "Privacy Policy"};
    ListView lsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        arrayOfOther = new ArrayList<ItemOther>();
        lsv = (ListView) findViewById(R.id.lsv_other);

        for (int i = 0; i < img.length; i++) {
            ItemOther it = new ItemOther(img[i], name[i]);
            arrayOfOther.add(it);
        }

        adapter = new OtherAdapter(OtherActivty.this, R.layout.other_lsv_row, arrayOfOther);
        lsv.setAdapter(adapter);

        lsv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    /*final Dialog mDialog = new Dialog(OtherActivty.this);
                    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					mDialog.setContentView(R.layout.about);
					mDialog.setCanceledOnTouchOutside(true);
					mDialog.setCancelable(true);
					mDialog.show();*/
                    Intent intmore = new Intent(OtherActivty.this, WebNews.class);
                    intmore.putExtra("link", "http://www.getinfocia.com");
                    startActivity(intmore);
                }

                if (position == 1) {
                    /*Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
					sendIntent.putExtra(Intent.EXTRA_TEXT, "Please a look at SortNews Application Free From Google Playstore -https://play.google.com/store/apps/details?id="+getPackageName());
					sendIntent.setType("text/plain");
					startActivity(sendIntent);*/
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("Text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL,
                            new String[]{getResources().getString(R.string.feedback)});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                            "Apps Feedback");
                    startActivity(Intent.createChooser(emailIntent,
                            "Send mail..."));

                }

				/*if(position==2)
                {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse(getString(R.string.play_more_apps))));
				}*/

                if (position == 2) {
                    Intent intmore = new Intent(OtherActivty.this, WebNews.class);
                    intmore.putExtra("link", "https://www.getinfocia.com/terms-conditions");
                    startActivity(intmore);
                }

                if (position == 3) {
                    /*final String appName = getPackageName();//your application package name i.e play store application url
                    try {
						startActivity(new Intent(Intent.ACTION_VIEW,
								Uri.parse("market://details?id="
										+ appName)));
					} catch (android.content.ActivityNotFoundException anfe) {
						startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://play.google.com/store/apps/details?id="
										+ appName)));
					}*/
                    Intent intmore = new Intent(OtherActivty.this, WebNews.class);
                    intmore.putExtra("link", "https://www.getinfocia.com/privacy-policy");
                    startActivity(intmore);

                }

            }
        });

        setupSettingsItem(findViewById(R.id.item_notifications), R.drawable.ic_notifications, R.string.notifications,
                R.drawable.ic_notifications_switch);
        setupSettingsItem(findViewById(R.id.item_images), R.drawable.ic_image, R.string.images,
                R.drawable.ic_images_switch);
        setupSettingsItem(findViewById(R.id.item_night_mode), R.drawable.ic_image, R.string.theme,
                R.drawable.ic_images_switch);
    }

    private void setupSettingsItem(View itemView, @DrawableRes int iconRes, @StringRes int textRes,
                                   @DrawableRes int switchRes) {
        ((ImageView) itemView.findViewById(R.id.ic_settings))
                .setImageResource(iconRes);
        ((TextView) itemView.findViewById(R.id.label_settings)).setText(textRes);
        final SwitchCompat switchView = itemView.findViewById(R.id.switch_settings);
        switchView.setThumbResource(switchRes);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView.setChecked(!switchView.isChecked());
            }
        });
    }

}
