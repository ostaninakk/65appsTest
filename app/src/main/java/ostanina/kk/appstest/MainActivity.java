package ostanina.kk.appstest;

import androidx.appcompat.app.AppCompatActivity;
import ostanina.kk.appstest.model.Employee;
import ostanina.kk.appstest.model.Specialty;
import ostanina.kk.appstest.ui.EmployeeListFragment;
import ostanina.kk.appstest.ui.Command.EmployeeSelectedCommand;
import ostanina.kk.appstest.ui.SpecialtyListFragment;
import ostanina.kk.appstest.ui.Command.SpecialtySelectedCommand;

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
        new SpecialtySelectedCommand(this, specialty).execute();
    }

    @Override
    public void onEmployeeSelected(Employee employee) {
        new EmployeeSelectedCommand(this, employee).execute();
    }
}
