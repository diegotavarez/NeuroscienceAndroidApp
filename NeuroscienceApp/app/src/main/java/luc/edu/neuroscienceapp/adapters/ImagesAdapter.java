package luc.edu.neuroscienceapp.adapters;

/**
 * Created by diegotavarez on 5/23/16.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.activities.ImageGrayscaleCardActivity;
import luc.edu.neuroscienceapp.entities.CardImage;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {

    private Context mContext;
    private List<CardImage> imageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, category, id;
        public ImageView thumbnail;
        public LinearLayout cardColor;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            category = (TextView) view.findViewById(R.id.category);
            id = (TextView) view.findViewById(R.id.id);
            cardColor = (LinearLayout) view.findViewById(R.id.card_color);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }

    }


    public ImagesAdapter(Context mContext, List<CardImage> albumList) {
        this.mContext = mContext;
        this.imageList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CardImage album = imageList.get(position);

        holder.title.setText(album.getName());
        holder.category.setText(album.getCategory());
        holder.id.setText(String.valueOf(album.getId()));
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCard = new Intent(mContext, ImageGrayscaleCardActivity.class);
                intentCard.putExtra("card_id", holder.id.getText().toString());
                intentCard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentCard);
            }
        });


        if (holder.category.getText().toString().equals("Natural Image")) {
            holder.cardColor.setBackgroundColor(Color.parseColor("#8bc34a"));
            holder.category.setTextColor(Color.parseColor("#8bc34a"));
        } else {
            holder.cardColor.setBackgroundColor(Color.parseColor("#EF6C00"));
            holder.category.setTextColor(Color.parseColor("#EF6C00"));

        }

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_images, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_see_patches:
                    Toast.makeText(mContext, "See Patches", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public final CardImage getItem(final int position) {
        return imageList.get(position);
    }

}
