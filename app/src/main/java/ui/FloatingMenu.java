package ui;

import android.content.Context;
import android.util.AttributeSet;

import com.awesome_folks.awesome_education.MainActivity;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by Ritesh on 4/16/2015.
 */
public class FloatingMenu extends FloatingActionsMenu {
    Context context;

    public FloatingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public FloatingMenu(Context context) {
        super(context);
        this.context = context;
    }

    public FloatingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    public void expand() {
        super.expand();
        ((MainActivity) context).onFabExpanded();
    }

    @Override
    public void collapse() {
        super.collapse();
        ((MainActivity) context).onFabCollapsed();
    }
}
