package ostanina.kk.appstest.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;
import ostanina.kk.appstest.R;
import ostanina.kk.appstest.factory.ViewModelFactory;
import ostanina.kk.appstest.data.model.Employee;
import ostanina.kk.appstest.data.model.Specialty;
import ostanina.kk.appstest.utils.Utils;
import ostanina.kk.appstest.ui.viewmodels.EmployeeViewModel;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;


public class EmployeeFragment extends DaggerFragment {

    private static final String ARG_EMPLOYEE_ID = "ARG_EMPLOYEE_ID";

    @Inject
    ViewModelFactory viewModelFactory;

    private int employeeId;
    private EmployeeViewModel employeeViewModel;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView birthdayTextView;
    private TextView ageTextView;
    private TextView specialtyTextView;

    public static EmployeeFragment newInstance(Employee employee) {
        Bundle args = new Bundle();
        args.putInt(ARG_EMPLOYEE_ID, employee.getId());
        EmployeeFragment fragment = new EmployeeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            employeeId = args.getInt(ARG_EMPLOYEE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lastNameTextView = view.findViewById(R.id.text_view_employee_last_name);
        firstNameTextView = view.findViewById(R.id.text_view_employee_first_name);
        birthdayTextView = view.findViewById(R.id.text_view_employee_birthday);
        ageTextView = view.findViewById(R.id.text_view_employee_age);
        specialtyTextView = view.findViewById(R.id.text_view_employee_specialty);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        employeeViewModel = ViewModelProviders.of(this, viewModelFactory).get(EmployeeViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers() {
        employeeViewModel.getEmployee(employeeId).observe(getViewLifecycleOwner(), new Observer<Employee>() {
            @Override
            public void onChanged(Employee employee) {
                updateUI(employee, null);
            }
        });

        employeeViewModel.getSpecialtiesForEmployee(employeeId).observe(getViewLifecycleOwner(), new Observer<List<Specialty>>() {
            @Override
            public void onChanged(List<Specialty> specialties) {
                updateUI(null, specialties);
            }
        });
    }

    private void updateUI(Employee employee, List<Specialty> specialties) {
        if (employee != null) {
            // Имя и фамилию выводить с заглавной буквы
            firstNameTextView.setText(Utils.firstUpperCaseInWord(employee.getFirstName()));
            lastNameTextView.setText(Utils.firstUpperCaseInWord(employee.getLastName()));
            String birthdayStr = employee.getBirthday();
            Date birthday = Utils.getDateFromString(birthdayStr);
            if (birthday != null) {
                // Дату вывести по формату "dd.mm.yyyy"
                String trueFormatBirthday = Utils.reformatDate(birthday);
                birthdayTextView.setText("(" + trueFormatBirthday + ")");
                // Посчитать возраст
                int age = Utils.calculateAge(birthday);
                // Получить год/года/лет на основе количества лет
                String ageWithYearText = getResources().getQuantityString(R.plurals.plurals_years, age, age);
                ageTextView.setText(ageWithYearText);
            } else {
                birthdayTextView.setText("-");
            }
        }

        if (specialties != null) {
            String specialtiesString = TextUtils.join(", ", specialties);
            specialtyTextView.setText(specialtiesString);
        }
    }
}
