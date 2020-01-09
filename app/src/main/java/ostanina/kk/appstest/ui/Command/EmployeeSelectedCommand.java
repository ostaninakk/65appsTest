package ostanina.kk.appstest.ui.Command;

import androidx.appcompat.app.AppCompatActivity;
import ostanina.kk.appstest.R;
import ostanina.kk.appstest.model.Employee;
import ostanina.kk.appstest.ui.Command.Command;
import ostanina.kk.appstest.ui.EmployeeFragment;

public class EmployeeSelectedCommand extends Command {
    private AppCompatActivity activity;
    private Employee employee;

    public EmployeeSelectedCommand(AppCompatActivity activity, Employee employee) {
        this.activity = activity;
        this.employee = employee;
    }

    @Override
    public boolean execute() {
        EmployeeFragment fragment = EmployeeFragment.newInstance(employee);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }
}
