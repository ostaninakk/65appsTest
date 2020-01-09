package ostanina.kk.appstest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ostanina.kk.appstest.R;
import ostanina.kk.appstest.ui.SpecialtyListFragment;
import ostanina.kk.appstest.model.Specialty;

public class SpecialtyRecyclerViewAdapter extends RecyclerView.Adapter<SpecialtyRecyclerViewAdapter.SpecialtyViewHolder> {
    private List<Specialty> specialties;
    private Context context;
    private SpecialtyListFragment.Callbacks callbacks;


    public SpecialtyRecyclerViewAdapter(Context context, SpecialtyListFragment.Callbacks callbacks) {
        this.context = context;
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public SpecialtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_specialty, parent, false);
        return new SpecialtyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialtyViewHolder holder, int position) {
        final Specialty specialty = specialties.get(position);
        holder.specialtyNameTextView.setText(specialty.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbacks.onSpecialtySelected(specialty);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (specialties!= null) {
            return specialties.size();
        }
        return 0;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
        notifyDataSetChanged();
    }

    static class SpecialtyViewHolder extends RecyclerView.ViewHolder {
        TextView specialtyNameTextView;

        SpecialtyViewHolder(@NonNull View itemView) {
            super(itemView);
            specialtyNameTextView = itemView.findViewById(R.id.text_view_specialty_name);
        }
    }
}
