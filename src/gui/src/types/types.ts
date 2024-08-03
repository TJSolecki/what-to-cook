export type Recipe = {
  id: number;
  imageUrl: string;
  name: string;
  author: string;
  cookTime: string;
  prepTime: string;
  totalTime: string;
  recipeUrl: string;
  recipeYield: string;
  nutrition: Nutrition;
};

export type Nutrition = {
  id: number;
  calories: string;
  carbohydrateContent: string;
  proteinContent: string;
  fatContent: string;
  saturatedFatContent: string;
  cholesterolContent: string;
  sodiumContent: string;
  fiberContent: string;
  sugarContent: string;
  unsaturatedFatContent: string;
  servingSize: string;
};
