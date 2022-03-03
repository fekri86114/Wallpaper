package org.rubikamp.wallpaper.model;

public class WallpaperModel {

    private String wallpaperImage;
    private String wallpaperName;

    public WallpaperModel(String wallpaperImage, String wallpaperName) {
        this.wallpaperImage = wallpaperImage;
        this.wallpaperName = wallpaperName;
    }

    public String getWallpaperImage() {
        return wallpaperImage;
    }

    public void setWallpaperImage(String wallpaperImage) {
        this.wallpaperImage = wallpaperImage;
    }

    public String getWallpaperName() {
        return wallpaperName;
    }

    public void setWallpaperName(String wallpaperName) {
        this.wallpaperName = wallpaperName;
    }
}
