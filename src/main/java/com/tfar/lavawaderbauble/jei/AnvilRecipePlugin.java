package com.tfar.lavawaderbauble.jei;

/*import com.google.common.collect.Lists;
import com.tfar.lavawaderbauble.AnvilRecipe;
import com.tfar.lavawaderbauble.AnvilRecipeHandler;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class AnvilRecipePlugin implements IModPlugin
{
	private IJeiHelpers jeiHelpers;
	public static IStackHelper stackHelper;


	@Override
	public void register(IModRegistry registry)
	{
		this.jeiHelpers = registry.getJeiHelpers();
		AnvilRecipePlugin.stackHelper = jeiHelpers.getStackHelper();

		List<IRecipeWrapper> anvilRecipes = new ArrayList<>();
		for (AnvilRecipe ar : AnvilRecipeHandler.getAllRecipes())
			anvilRecipes.add(jeiHelpers.getVanillaRecipeFactory().createAnvilRecipe(ar.getFirst(), Lists.newArrayList(ar.getSecond()), Lists.newArrayList(ar.getOutput())));
		registry.addRecipes(anvilRecipes, VanillaRecipeCategoryUid.ANVIL);
		//DescriptionHandler.addDescriptions(registry);
	}

}*/
