package org.rubikamp.wallpaper.fragments;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.rubikamp.wallpaper.R;
import org.rubikamp.wallpaper.adapter.WallpaperAdapter;
import org.rubikamp.wallpaper.databinding.FragmentHomeBinding;
import org.rubikamp.wallpaper.dialog.DeleteBottomSheetDialog;
import org.rubikamp.wallpaper.model.WallpaperModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements WallpaperAdapter.SetOnItemClickListener, WallpaperAdapter.SetOnMenuClickListener, DeleteBottomSheetDialog.OnItemDeleteListener {

    private FragmentHomeBinding binding;
    private WallpaperAdapter wallpaperAdapter;
    private int position;
    private DeleteBottomSheetDialog bottomSheetDialog;
    private List<WallpaperModel> listItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setuprecyclerview();
    }

    private void setuprecyclerview() {
        wallpaperAdapter = new WallpaperAdapter(setListData(), this, this);
        binding.recyclerviewWallpaper.setHasFixedSize(true);
//        binding.recyclerviewWallpaper.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        binding.recyclerviewWallpaper.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerviewWallpaper.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerviewWallpaper.setAdapter(wallpaperAdapter);

    }

    private List<WallpaperModel> setListData() {
        listItem = new ArrayList<>();
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/1371749998/blue-red-abstract-from-s22_400x225-mm-90.webp", "First Item"));
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/1371749998/matebook-x-pro_400x225-mm-90.webp", "Second Item"));
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/1371749998/view-from-a-drone-above-the-ocean-shore_400x225-mm-90.webp", "Third Item"));
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/3309293265/space-shiptwo_600x338-mm-90.webp", "Fourth Item"));
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/1371749998/gta-6-poster_400x225-mm-90.webp", "Fifth Item"));
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/1491662440/flintlock-the-siege-of-dawn_400x225-mm-90.webp", "Sixth Item"));
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/1371749998/gta-6-gameplay-car_400x225-mm-90.webp", "Seventh Item"));
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/1371749998/gta-6-screenshot_400x225-mm-90.webp", "eghith Item"));
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/3309293265/rolls-royce-front-side_400x225-mm-90.webp", "ninth Item"));
        listItem.add(new WallpaperModel("https://uhdwallpapers.org/uploads/cache/1371749998/white-blue-orange-abstract-from-s22_400x225-mm-90.webp", "ten Item"));
        return listItem;
    }

    @Override
    public void ItemClicked(WallpaperModel wallpaperModel, int position) {
        this.position = position;
        Bundle bundle = new Bundle();
        bundle.putSerializable("WALLPAPER_MODEL", wallpaperModel);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_nav_home_to_detailsFragment, bundle);
    }

    @Override
    public void MenuClicked(View view) {
        showPopupMenu(view);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.delete:
                    showDeleteBottomSheet();
                    break;
                case R.id.set_wallpaper:
                    showDialogSetAsWallpaper();
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    private void showDeleteBottomSheet() {
        bottomSheetDialog = new DeleteBottomSheetDialog();
        bottomSheetDialog.show(getChildFragmentManager(), "DELETE_BOTTOM_SHEET_DIALOG");
    }

    private void showDialogSetAsWallpaper() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(getString(R.string.wallpaper_title));
        alertDialog.setMessage(R.string.dialog_message);
        alertDialog.setPositiveButton("YES", (dialogInterface, i) -> setAsWallpaper());

        alertDialog.setNegativeButton("NO", (dialogInterface, i) -> cancelMessage());

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void cancelMessage() {
        Toast.makeText(requireContext(), "Cancelled!!", Toast.LENGTH_SHORT).show();
    }

    public void setAsWallpaper() {
        Glide.with(requireContext())
                .asBitmap()
                .load(listItem.get(position).getWallpaperImage())
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        try {
                            WallpaperManager.getInstance(requireContext()).setBitmap(resource);
                            Toast.makeText(requireContext(), "Yesssss...!!!", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void onItemDelete() {
        wallpaperAdapter.deleteItem(position);
        bottomSheetDialog.dismiss();
    }
}



