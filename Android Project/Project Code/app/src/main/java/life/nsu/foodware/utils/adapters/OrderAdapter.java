package life.nsu.foodware.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import life.nsu.foodware.R;
import life.nsu.foodware.models.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    int selection;

    ArrayList<Order> orderList;

    public OrderAdapter(Context context, ArrayList<Order> orderList, int select) {
        this.context = context;
        this.orderList = orderList;
        selection = select;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);

        if (orderList != null) {
            holder.mOrderId.setText(order.getOrderId());
            holder.mStatus.setText(order.getStatus());


            if (selection == 1) {
                holder.mLocation.setVisibility(View.VISIBLE);
                holder.mLocation.setText(order.getLocation());
            } else {
                holder.mLocation.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (orderList == null) {
            return 0;
        } else {
            return orderList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mIcon;

        TextView mOrderId;
        TextView mStatus;
        TextView mLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.iv_icon);
            mOrderId = itemView.findViewById(R.id.tv_order_id);
            mStatus = itemView.findViewById(R.id.tv_price);
            mLocation = itemView.findViewById(R.id.tv_location);
        }
    }
}
