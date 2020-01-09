package ostanina.kk.appstest.ui.Command;

import androidx.appcompat.app.AppCompatActivity;
import ostanina.kk.appstest.R;
import ostanina.kk.appstest.model.Specialty;
import ostanina.kk.appstest.ui.EmployeeListFragment;

public class SpecialtySelectedCommand extends Command {
    private AppCompatActivity activity;
    private Specialty specialty;

    public SpecialtySelectedCommand(AppCompatActivity activity, Specialty specialty) {
        this.activity = activity;
        this.specialty = specialty;
    }

    @Override
    public boolean execute() {
        EmployeeListFragment fragment = EmployeeListFragment.newInstance(specialty);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }
}
