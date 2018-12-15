package fq.campus;

import android.os.Bundle;

import fq.campus.base.tabActivity;

public class ProfileActivity extends tabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle(R.string.profile_title);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fqTab = findViewById(R.id.fqTab);
        tabIni();
        fqTab.addOnTabSelectedListener(onFqTabSelectedListener);

    }
}
