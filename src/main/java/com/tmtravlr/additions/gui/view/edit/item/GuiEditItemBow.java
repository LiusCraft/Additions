package com.tmtravlr.additions.gui.view.edit.item;

import java.util.List;
import java.util.stream.Collectors;

import com.tmtravlr.additions.addon.Addon;
import com.tmtravlr.additions.addon.items.ItemAddedBow;
import com.tmtravlr.additions.addon.items.ItemAddedShield;
import com.tmtravlr.additions.gui.view.components.input.GuiComponentBooleanInput;
import com.tmtravlr.additions.gui.view.components.input.GuiComponentFloatInput;
import com.tmtravlr.additions.gui.view.components.input.GuiComponentIntegerInput;
import com.tmtravlr.additions.gui.view.components.input.GuiComponentIngredientOreNBTInput;
import com.tmtravlr.additions.gui.view.components.input.GuiComponentListInput;
import com.tmtravlr.additions.gui.view.components.input.dropdown.GuiComponentDropdownInputItem;
import com.tmtravlr.additions.gui.view.components.input.dropdown.GuiComponentDropdownInputSoundEvent;
import com.tmtravlr.additions.gui.view.edit.texture.GuiEditItemBowTexture;
import com.tmtravlr.additions.gui.view.edit.texture.GuiEditItemTexture;
import com.tmtravlr.additions.util.models.ItemModelManager;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Page for adding a new simple item or editing an existing one.
 * 
 * @author Tmtravlr (Rebeca Rey)
 * @since November 2017
 */
public class GuiEditItemBow extends GuiEditItem<ItemAddedBow> {
	
	private GuiComponentIntegerInput itemDurabilityInput;
	private GuiComponentIntegerInput itemEnchantabilityInput;
	private GuiComponentIngredientOreNBTInput itemRepairStacksInput;
	private GuiComponentFloatInput itemExtraDamageInput;
	private GuiComponentFloatInput itemScatteringInput;
	private GuiComponentIntegerInput itemDrawTimeInput;
	private GuiComponentIntegerInput itemShotCountInput;
	private GuiComponentBooleanInput itemFiresVanillaArrowsInput;
	private GuiComponentBooleanInput itemAlwaysInfiniteInput;
	private GuiComponentBooleanInput itemConsumesOneAmmoInput;
	private GuiComponentFloatInput itemEfficiencyMultiplierInput;
	private GuiComponentDropdownInputSoundEvent itemShotSoundInput;
	private GuiComponentListInput<GuiComponentDropdownInputItem> itemAmmoItemsInput;

	public GuiEditItemBow(GuiScreen parentScreen, String title, Addon addon, ItemAddedBow item) {
		super(parentScreen, title, addon);
		
		this.isNew = item == null;
		
		if (this.isNew) {
			this.item = new ItemAddedBow();
		} else {
			this.item = item;
		}
	}

	@Override
	public void initComponents() {
		super.initComponents();
		
		this.itemDurabilityInput = new GuiComponentIntegerInput(I18n.format("gui.edit.itemMaterial.baseToolDurability.label"), this, false);
		this.itemDurabilityInput.setMinimum(0);
		this.itemDurabilityInput.setRequired();
		this.itemDurabilityInput.setInfo(new TextComponentTranslation("gui.edit.itemMaterial.baseToolDurability.info"));
		if (!this.isNew) {
			this.itemDurabilityInput.setDefaultInteger(this.item.getMaxDamage());
		}
		
		this.itemEnchantabilityInput = new GuiComponentIntegerInput(I18n.format("gui.edit.item.enchantability.label"), this, false);
		this.itemEnchantabilityInput.setMinimum(0);
		this.itemEnchantabilityInput.setInfo(new TextComponentTranslation("gui.edit.enchantability.info"));
		if (!this.isNew) {
			this.itemEnchantabilityInput.setDefaultInteger(this.item.enchantability);
		}
		
		this.itemRepairStacksInput = new GuiComponentIngredientOreNBTInput(I18n.format("gui.edit.item.repairStacks.label"), this);
		if (!this.isNew) {
			this.itemRepairStacksInput.setDefaultIngredient(this.item.repairStacks);
		}

		this.itemExtraDamageInput = new GuiComponentFloatInput(I18n.format("gui.edit.item.shooter.damage.label"), this, false);
		this.itemExtraDamageInput.setMinimum(0f);
		if (!this.isNew) {
			this.itemExtraDamageInput.setDefaultFloat(this.item.extraDamage);
		}

		this.itemScatteringInput = new GuiComponentFloatInput(I18n.format("gui.edit.item.shooter.scattering.label"), this, false);
		this.itemScatteringInput.setMinimum(0f);
		if (!this.isNew) {
			this.itemScatteringInput.setDefaultFloat(this.item.scattering);
		}

		this.itemDrawTimeInput = new GuiComponentIntegerInput(I18n.format("gui.edit.item.bow.drawTime.label"), this, false);
		this.itemDrawTimeInput.setMinimum(1);
		this.itemDrawTimeInput.setMaximum(72000);
		if (!this.isNew) {
			this.itemDrawTimeInput.setDefaultInteger(this.item.drawTime);
		} else {
			this.itemDrawTimeInput.setDefaultInteger(20);
		}

		this.itemShotCountInput = new GuiComponentIntegerInput(I18n.format("gui.edit.item.shooter.shotCount.label"), this, false);
		this.itemShotCountInput.setMinimum(1);
		this.itemShotCountInput.setMaximum(100);
		if (!this.isNew) {
			this.itemShotCountInput.setDefaultInteger(this.item.shotCount);
		}

		this.itemFiresVanillaArrowsInput = new GuiComponentBooleanInput(I18n.format("gui.edit.item.shooter.firesVanillaArrows.label"), this);
		if (!this.isNew) {
			this.itemFiresVanillaArrowsInput.setDefaultBoolean(this.item.firesVanillaArrows);
		} else {
			this.itemFiresVanillaArrowsInput.setDefaultBoolean(true);
		}

		this.itemAlwaysInfiniteInput = new GuiComponentBooleanInput(I18n.format("gui.edit.item.shooter.alwaysInfinite.label"), this);
		if (!this.isNew) {
			this.itemAlwaysInfiniteInput.setDefaultBoolean(this.item.alwaysInfinite);
		}
		
		this.itemConsumesOneAmmoInput = new GuiComponentBooleanInput(I18n.format("gui.edit.item.shooter.consumesOneAmmo.label"), this);
		this.itemConsumesOneAmmoInput.setInfo(new TextComponentTranslation("gui.edit.item.shooter.consumesOneAmmo.info"));
		if (!this.isNew) {
			this.itemConsumesOneAmmoInput.setDefaultBoolean(this.item.consumesOneAmmo);
		}
		
		this.itemEfficiencyMultiplierInput = new GuiComponentFloatInput(I18n.format("gui.edit.item.efficiencyMultiplier.label"), this, false);
		this.itemEfficiencyMultiplierInput.setInfo(new TextComponentTranslation("gui.edit.item.efficiencyMultiplier.info"));
		this.itemEfficiencyMultiplierInput.setMaximum(1);
		this.itemEfficiencyMultiplierInput.setMinimum(0);
		if (!this.isNew) {
			this.itemEfficiencyMultiplierInput.setDefaultFloat(this.item.efficiencyMultiplier);
		} else {
			this.itemEfficiencyMultiplierInput.setDefaultFloat(0.5f);
		}
		
		this.itemShotSoundInput = new GuiComponentDropdownInputSoundEvent(I18n.format("gui.edit.item.shooter.shotSound.label"), this, this.addon);
		if (!this.isNew) {
			this.itemShotSoundInput.setDefaultSelected(this.item.shotSound);
		} else {
			this.itemShotSoundInput.setDefaultSelected(SoundEvents.ENTITY_ARROW_SHOOT);
		}
		
		this.itemAmmoItemsInput = new GuiComponentListInput<GuiComponentDropdownInputItem>(I18n.format("gui.edit.item.shooter.ammoItems.label"), this) {

			@Override
			public GuiComponentDropdownInputItem createBlankComponent() {
				GuiComponentDropdownInputItem input = new GuiComponentDropdownInputItem("", this.editScreen);
				return input;
			}
		};
		if (!this.isNew) {
			List<GuiComponentDropdownInputItem> ammoItemInputs = this.item.ammoItems.stream().map(ammoItem -> {
				GuiComponentDropdownInputItem input = this.itemAmmoItemsInput.createBlankComponent();
				input.setDefaultSelected(ammoItem);
				return input;
			}).collect(Collectors.toList());
			
			this.itemAmmoItemsInput.setDefaultComponents(ammoItemInputs);
		}
		
		if (this.copyFrom != null) {
			this.copyFromOther();
		}
		
		this.components.add(this.itemIdInput);
		this.components.add(this.itemNameInput);
		this.components.add(this.itemShinesInput);
		this.components.add(this.itemDurabilityInput);
		this.components.add(this.itemEnchantabilityInput);
		this.components.add(this.itemRepairStacksInput);
		this.components.add(this.itemExtraDamageInput);
		this.components.add(this.itemDrawTimeInput);
		this.components.add(this.itemShotCountInput);
		this.components.add(this.itemAlwaysInfiniteInput);
		if (!this.isNew) {
			this.components.add(this.itemTextureButton);
		}
		
		this.advancedComponents.add(this.itemFiresVanillaArrowsInput);
		this.advancedComponents.add(this.itemConsumesOneAmmoInput);
		this.advancedComponents.add(this.itemAmmoItemsInput);
		this.advancedComponents.add(this.itemScatteringInput);
		this.advancedComponents.add(this.itemEfficiencyMultiplierInput);
		this.advancedComponents.add(this.itemShotSoundInput);
		this.advancedComponents.add(this.itemTooltipInput);
		this.advancedComponents.add(this.itemOreDictInput);
		this.advancedComponents.add(this.itemBurnTimeInput);
		this.advancedComponents.add(this.itemContainerInput);
		this.advancedComponents.add(this.itemAttributesInput);
	}
	
	@Override
	public void saveObject() {
		
		this.item.setMaxDamage(this.itemDurabilityInput.getInteger());
		this.item.enchantability = this.itemEnchantabilityInput.getInteger();
		this.item.repairStacks = this.itemRepairStacksInput.getIngredient();
		this.item.extraDamage = this.itemExtraDamageInput.getFloat();
		this.item.scattering = this.itemScatteringInput.getFloat();
		this.item.drawTime = this.itemDrawTimeInput.getInteger();
		this.item.shotCount = this.itemShotCountInput.getInteger();
		this.item.firesVanillaArrows = this.itemFiresVanillaArrowsInput.getBoolean();
		this.item.alwaysInfinite = this.itemAlwaysInfiniteInput.getBoolean();
		this.item.consumesOneAmmo = this.itemConsumesOneAmmoInput.getBoolean();
		this.item.efficiencyMultiplier = this.itemEfficiencyMultiplierInput.getFloat();
		this.item.shotSound = this.itemShotSoundInput.getSelected();
		this.item.ammoItems = this.itemAmmoItemsInput.getComponents().stream().map(GuiComponentDropdownInputItem::getSelected).collect(Collectors.toList());
		
		super.saveObject();
	}
	
	@Override
	protected void copyFromOther() {
		this.itemDurabilityInput.setDefaultInteger(this.copyFrom.getMaxDamage());
		this.itemEnchantabilityInput.setDefaultInteger(this.copyFrom.enchantability);
		this.itemRepairStacksInput.setDefaultIngredient(this.copyFrom.repairStacks);
		this.itemExtraDamageInput.setDefaultFloat(this.copyFrom.extraDamage);
		this.itemScatteringInput.setDefaultFloat(this.copyFrom.scattering);
		this.itemDrawTimeInput.setDefaultInteger(this.copyFrom.drawTime);
		this.itemShotCountInput.setDefaultInteger(this.copyFrom.shotCount);
		this.itemFiresVanillaArrowsInput.setDefaultBoolean(this.copyFrom.firesVanillaArrows);
		this.itemAlwaysInfiniteInput.setDefaultBoolean(this.copyFrom.alwaysInfinite);
		this.itemConsumesOneAmmoInput.setDefaultBoolean(this.copyFrom.consumesOneAmmo);
		this.itemEfficiencyMultiplierInput.setDefaultFloat(this.copyFrom.efficiencyMultiplier);
		this.itemShotSoundInput.setDefaultSelected(this.copyFrom.shotSound);
		
		List<GuiComponentDropdownInputItem> ammoItemInputs = this.copyFrom.ammoItems.stream().map(ammoItem -> {
			GuiComponentDropdownInputItem input = this.itemAmmoItemsInput.createBlankComponent();
			input.setDefaultSelected(ammoItem);
			return input;
		}).collect(Collectors.toList());
		
		this.itemAmmoItemsInput.setDefaultComponents(ammoItemInputs);
		
		super.copyFromOther();
	}
    
	@Override
	protected void openTextureDialogue() {
    	GuiScreen nextScreen;
    	if (this.isNew) {
    		 nextScreen = getItemCreatedPopup();
    	} else {
    		nextScreen = this;
    	}
    	
    	this.mc.displayGuiScreen(new GuiEditItemBowTexture(nextScreen, this.addon, this.item, this.isNew));
    }
}