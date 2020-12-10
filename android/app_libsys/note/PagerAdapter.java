package com.example.app_libsys.note;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int num;
    private String id_res;
    List<Fragment> fragments=new ArrayList<>();

    public PagerAdapter(FragmentManager fm, int num, String id_res) {
        super(fm);
        this.num = num;
//        fragments.add(new Fragment1());
//        fragments.add(new Fragment2());
//        fragments.add(new Fragment3());
        this.id_res = id_res;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Fragment1 fragment1 = new Fragment1();
                Bundle bundle = new Bundle();
                bundle.putString("id", id_res);
                fragment1.setArguments(bundle);
                return fragment1;
            case 1:
                Fragment2 fragment2 = new Fragment2();
                return fragment2;
            case 2:
                Fragment3 fragment3 = new Fragment3();
                return fragment3;

                default:
                    return null;

        }

//        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return num;
//        return fragments.size();
    }
}