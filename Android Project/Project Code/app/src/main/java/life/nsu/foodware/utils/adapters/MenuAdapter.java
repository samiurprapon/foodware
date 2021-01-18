package life.nsu.foodware.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import life.nsu.foodware.R;
import life.nsu.foodware.models.MenuItem;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    Context context;
    int selection;

    ArrayList<MenuItem> menuItems;

    public MenuAdapter(Context context, ArrayList<MenuItem> menuItems, int select) {
        this.context = context;
        this.menuItems = menuItems;
        selection = select;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new MenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MenuItem menuItem = menuItems.get(position);

        if(menuItems != null) {
            holder.mMenuName.setText(menuItem.getName());
            holder.mPrice.setText(menuItem.getPrice());

            if(menuItems.get(position).getImageUrl() != null) {
                Glide.with(context)
                        .load(menuItem.getImageUrl())
                        .placeholder(R.drawable.ic_logo)
                        .fitCenter()
                        .into(holder.mIcon);
            }

            if(selection == 1) {
                holder.mAvailable.setChecked(menuItem.isAvailable());
                holder.mAvailable.setVisibility(View.VISIBLE);
            } else {
                holder.mAvailable.setVisibility(View.GONE);
            }
        }

        holder.mAvailable.setOnCheckedChangeListener((buttonView, isChecked) -> {
            holder.mAvailable.setChecked(isChecked);

            // call server to collect and store data
        });

    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ToggleButton mAvailable;
        ImageView mIcon;

        TextView mMenuName;
        TextView mPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMenuName = itemView.findViewById(R.id.tv_order_id);
            mPrice = itemView.findViewById(R.id.tv_price);
            mAvailable = itemView.findViewById(R.id.tb_available);
            mIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
