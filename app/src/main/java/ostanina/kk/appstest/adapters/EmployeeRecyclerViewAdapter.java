package ostanina.kk.appstest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ostanina.kk.appstest.EmployeeListFragment;
import ostanina.kk.appstest.R;
import ostanina.kk.appstest.model.Employee;
import ostanina.kk.appstest.utils.Utils;

public class EmployeeRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeRecyclerViewAdapter.EmployeeViewHolder> {
    private List<Employee> employees;
    private Context context;
    private EmployeeListFragment.Callbacks callbacks;

    public EmployeeRecyclerViewAdapter(Context context, EmployeeListFragment.Callbacks callbacks) {
        this.context = context;
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.bind(employees.get(position));
    }

    @Override
    public int getItemCount() {
        if (employees!= null) {
            return employees.size();
        }
        return 0;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView employeeNameTextView;
        TextView employeeAgeTextView;
        Employee employee;


        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeNameTextView = itemView.findViewById(R.id.text_view_employee_name);
            employeeAgeTextView = itemView.findViewById(R.id.text_view_employee_age);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callbacks.onEmployeeSelected(employee);
                }
            });
        }

        public void bind(Employee employee) {
            this.employee = employee;
            String firstName = Utils.firstUpperCaseInWord(employee.getFirstName());
            String lastName = Utils.firstUpperCaseInWord(employee.getLastName());
            String name = firstName + " " + lastName;
            employeeNameTextView.setText(name);
            // calculate age and add "year/years"
            String birthdayStr = employee.getBirthday();
            Date birthday = Utils.getDateFromString(birthdayStr);
            if (birthday != null) {
                int age = Utils.calculateAge(birthday);
                String ageWithYearText = context.getResources().getQuantityString(R.plurals.plurals_years, age, age);
                employeeAgeTextView.setText(ageWithYearText);
            }
        }
    }
}