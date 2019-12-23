package ostanina.kk.appstest;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ostanina.kk.appstest.adapters.EmployeeRecyclerViewAdapter;
import ostanina.kk.appstest.model.Employee;
import ostanina.kk.appstest.model.Specialty;
import ostanina.kk.appstest.viewmodels.EmployeeListViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class EmployeeListFragment extends Fragment {
    /**
     *Required interface for hosting activities
     */
    public interface Callbacks{
        void onEmployeeSelected(Employee employee);
    }

    private final static String ARG_SPECIALTY_ID = "ARG_SPECIALTY_ID";

    private int specialtyId;
    private EmployeeListViewModel employeeListViewModel;
    private RecyclerView recyclerView;
    private EmployeeRecyclerViewAdapter adapter;
    private Callbacks listener;


    public static EmployeeListFragment newInstance(Specialty specialty) {
        Bundle args = new Bundle();
        args.putInt(ARG_SPECIALTY_ID, specialty.getId());
        EmployeeListFragment fragment = new EmployeeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EmployeeListFragment.Callbacks) {
            listener = (EmployeeListFragment.Callbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Callbacks interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            specialtyId = args.getInt(ARG_SPECIALTY_ID);
        }
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        initRecyclerView();
     }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        employeeListViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel.class);
        subscribeObservers();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    private void subscribeObservers() {
        employeeListViewModel.getEmployeesForSpecialty(specialtyId)
                .observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                adapter.setEmployees(employees);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EmployeeRecyclerViewAdapter(getContext(), listener);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
