package com.tennis.cli.tenn.cus.tennisapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.murgupluoglu.flagkit.FlagKit;
import com.tennis.cli.tenn.cus.tennisapp.Models.PlayersModel;
import com.tennis.cli.tenn.cus.tennisapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopMaleAdapter extends ListAdapter<PlayersModel,TopMaleAdapter.MaleViewHolder> {

    private Context context;

    public interface OnItemClickListener{

        void onFlagIsClicked(PlayersModel playersModel);
        void onPlayerClicked(PlayersModel playersModel);
    }

    public OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TopMaleAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    public static DiffUtil.ItemCallback<PlayersModel> diffCallback = new DiffUtil.ItemCallback<PlayersModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull PlayersModel oldItem, @NonNull PlayersModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PlayersModel oldItem, @NonNull PlayersModel newItem) {
            return oldItem.getProfileImg().contentEquals(newItem.getProfileImg()) &&
                    oldItem.getFlatImg().contentEquals(newItem.getFlatImg()) &&
                    oldItem.getName().contentEquals(newItem.getName());
        }
    };

    @NonNull
    @Override
    public MaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.top_adpt_lyt,parent,false);
        return new MaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaleViewHolder holder, int position) {

        PlayersModel playersModel = getItem(position);

        System.out.println("COUNTRY NAME IS: " + playersModel.getCountry());

//        int flag =  FlagKit.INSTANCE.getResId(context,playersModel.getCountry());
//
//        holder.flagImg.setImageResource(flag);
        if (playersModel.getGender().contentEquals("M")){
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.male)).into(holder.profileImg);
        }else{
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.female)).into(holder.profileImg);
        }
        Glide.with(context).load(context.getResources().getDrawable(R.drawable.ic_flag)).into(holder.flagImg);
        holder.nameTxt.setText(playersModel.getName());
        holder.ageTxt.setText(playersModel.getAge() + " y.o");
        holder.pointsTxt.setText(playersModel.getPoints() + " points");


    }

    public class MaleViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView flagImg,profileImg;
        private AppCompatTextView nameTxt,ageTxt,pointsTxt;

        public MaleViewHolder(@NonNull View itemView) {
            super(itemView);

            flagImg = itemView.findViewById(R.id.flag_img);
            profileImg = itemView.findViewById(R.id.profile_img);
            nameTxt = itemView.findViewById(R.id.name_txt);
            ageTxt = itemView.findViewById(R.id.age_txt);
            pointsTxt = itemView.findViewById(R.id.points_txt);

            flagImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int p = getAdapterPosition();

                    if (onItemClickListener != null &&  p != RecyclerView.NO_POSITION){

                        onItemClickListener.onFlagIsClicked(getItem(p));
                    }
                }
            });

            profileImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int p = getAdapterPosition();

                    if (onItemClickListener != null &&  p != RecyclerView.NO_POSITION){

                        onItemClickListener.onPlayerClicked(getItem(p));
                    }
                }
            });

        }
    }
}
