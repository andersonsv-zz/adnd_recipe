package br.com.andersonsv.recipe.data;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

import br.com.andersonsv.recipe.R;

public enum ImageRecipe {

    NUTELLA_PIE_1(R.drawable.nutellapie),
    BROWNIE_2(R.drawable.brownie),
    YELLOW_CAKE_3(R.drawable.yellowcake),
    CHEESECAKE_4(R.drawable.cheesecake);

    private int imageRecipe;

    private static final SparseArray<ImageRecipe> imageById = new SparseArray<>();

    static {

        for (final ImageRecipe image : values()) {
            imageById.put(image.ordinal() +1, image);
        }
    }

    public static ImageRecipe getById(final int id) {
        ImageRecipe result = imageById.get(id);
        if (result != null) {
            return result;
        }else{
            return null;
        }
    }

    ImageRecipe(final int imageRecipe){
        this.imageRecipe = imageRecipe;
    }

    public int getImageRecipe() {
        return imageRecipe;
    }


}
