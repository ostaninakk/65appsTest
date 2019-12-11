package ostanina.kk.appstest;

import androidx.appcompat.app.AppCompatActivity;
import ostanina.kk.appstest.model.Employee;
import ostanina.kk.appstest.model.Specialty;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SpecialtyListFragment.Callbacks,
    EmployeeListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SpecialtyListFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onSpecialtySelected(Specialty specialty) {
        EmployeeListFragment fragment = EmployeeListFragment.newInstance(specialty);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEmployeeSelected(Employee employee) {
        EmployeeFragment fragment = EmployeeFragment.newInstance(employee);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
