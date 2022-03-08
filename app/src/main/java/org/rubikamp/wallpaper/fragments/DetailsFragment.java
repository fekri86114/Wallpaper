package org.rubikamp.wallpaper.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.rubikamp.wallpaper.databinding.FragmentDetailsBinding;
import org.rubikamp.wallpaper.model.WallpaperModel;


public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private WallpaperModel wallpaperModel;

    private static final String TAG = "DetailsFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wallpaperModel = (WallpaperModel) getArguments().getSerializable("WALLPAPER_MODEL");
        Log.i(TAG, "onCreate: " +  wallpaperModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(requireActivity()).load(wallpaperModel.getWallpaperImage()).into(binding.imageviewWallpaperDetails);
        binding.textviewWallpaperNameDetails.setText(wallpaperModel.getWallpaperName());
    }
}
