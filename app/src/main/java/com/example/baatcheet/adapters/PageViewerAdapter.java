package com.example.baatcheet.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.example.baatcheet.fragments.Chats;
import com.example.baatcheet.fragments.Friends;
import com.example.baatcheet.fragments.Updates;

public class PageViewerAdapter extends FragmentPagerAdapter {

    public PageViewerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Chats();
            case 1:
                return new Friends();
            case 2:
                return new Updates();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}