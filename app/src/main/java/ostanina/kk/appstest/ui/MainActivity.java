package ostanina.kk.appstest.ui;

import dagger.android.support.DaggerAppCompatActivity;
import ostanina.kk.appstest.R;
import ostanina.kk.appstest.data.model.Employee;
import ostanina.kk.appstest.data.model.Specialty;
import ostanina.kk.appstest.ui.command.EmployeeSelectedCommand;
import ostanina.kk.appstest.ui.command.SpecialtySelectedCommand;

import android.os.Bundle;

public class MainActivity extends DaggerAppCompatActivity implements SpecialtyListFragment.Callbacks,
    EmployeeListFragment.Callbacks  {

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
        new SpecialtySelectedCommand(this, specialty).execute();
    }

    @Override
    public void onEmployeeSelected(Employee employee) {
        new EmployeeSelectedCommand(this, employee).execute();
    }
}
