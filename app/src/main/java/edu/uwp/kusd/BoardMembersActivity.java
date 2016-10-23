package edu.uwp.kusd;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BoardMembersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_members);


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_board_members_fragment_container);

        if (fragment == null) {
            Fragment BoardMembersFragment = new BoardMembersFragment();
            fragmentManager.beginTransaction().add(R.id.activity_board_members_fragment_container, BoardMembersFragment).commit();
        }
    }
}
