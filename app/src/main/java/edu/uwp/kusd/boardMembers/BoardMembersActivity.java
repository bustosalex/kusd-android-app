package edu.uwp.kusd.boardMembers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import edu.uwp.kusd.R;

public class BoardMembersActivity extends AppCompatActivity {

    /**
     * Handles actions to perform when the activity is created
     *
     * @param savedInstanceState the saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_members);

        //Setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.board_member_activity_title);


        //Attach the fragment to the activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_board_members_fragment_container);

        if (fragment == null) {
            Fragment BoardMembersFragment = new BoardMembersFragment();
            fragmentManager.beginTransaction().add(R.id.activity_board_members_fragment_container, BoardMembersFragment).commit();
        }
    }
}
