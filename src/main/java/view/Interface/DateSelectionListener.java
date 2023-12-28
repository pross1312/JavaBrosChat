
package view.Interface;

import java.util.Date;

public interface DateSelectionListener {
    void onDateSelected(Date startDate, Date endDate);
    void onDateSelectedForActiveUser(Date startDate, Date endDate);
}
