package com.tmtravlr.additions.gui.view.edit;

import java.util.Collection;

import com.tmtravlr.additions.addon.Addon;
import com.tmtravlr.additions.gui.view.components.GuiComponentButton;
import com.tmtravlr.additions.gui.view.components.GuiComponentDisplayText;
import com.tmtravlr.additions.gui.view.components.input.GuiComponentDropdownInput;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Shows a list of types you can select.
 * 
 * @author Tmtravlr (Rebeca Rey)
 * @since September 2017 
 */
public abstract class GuiEditSelectType<T> extends GuiEdit {
	
	protected Addon addon;
	
	private GuiComponentDropdownInput<T> componentTypeList;
	private GuiComponentDisplayText componentDescription;
	private GuiComponentButton componentcreateButton;
	
	private int CREATE_BUTTON = this.buttonCount++;
	
	public GuiEditSelectType(GuiScreen parentScreen, String title, Addon addon) {
		super(parentScreen, title);
		this.addon = addon;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		this.buttonList.removeIf(button -> button.id == SAVE_BUTTON);
	}
	
	@Override
	public void cancelEdit() {
		this.mc.displayGuiScreen(parentScreen);
	}
	
	@Override
	public void notifyHasChanges() {
		if (this.componentTypeList.getSelected() == null) {
			this.componentDescription.setDisplayText(new TextComponentTranslation("gui.edit.selectType.descrpition.default"));
			this.componentcreateButton.visible = false;
		} else {
			String description = this.getDescription(this.componentTypeList.getSelected());
			this.componentcreateButton.visible = true;
			
			if (description == null || description.isEmpty()) {
				this.componentDescription.setDisplayText(new TextComponentTranslation("gui.edit.selectType.descrpition.none"));
			} else {
				this.componentDescription.setDisplayText(new TextComponentString(description));
			}
		}
	}

	@Override
	public void initComponents() {
		this.componentTypeList = new GuiComponentDropdownInput<>(I18n.format("gui.edit.selectType.label"), this);
		this.componentTypeList.setSelections(this.getTypes());
		
		this.componentDescription = new GuiComponentDisplayText(this, new TextComponentTranslation("gui.edit.selectType.descrpition.default"));
		
		this.componentcreateButton = new GuiComponentButton(this, CREATE_BUTTON, I18n.format("gui.buttons.create"));
		this.componentcreateButton.visible = false;
		
		this.components.add(this.componentTypeList);
		this.components.add(this.componentDescription);
		this.components.add(this.componentcreateButton);
	}
	
    @Override
    protected void actionPerformed(GuiButton button) {
    	if (button.id == CREATE_BUTTON) {
    		createObject();
    	} else {
    		super.actionPerformed(button);
    	}
    }
    
    protected T getSelectedType() {
    	return this.componentTypeList.getSelected();
    }
	
	public abstract Collection<T> getTypes();
	
	public abstract String getDescription(T type);
	
	public abstract void createObject();
}
