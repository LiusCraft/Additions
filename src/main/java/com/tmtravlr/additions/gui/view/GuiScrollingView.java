package com.tmtravlr.additions.gui.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tmtravlr.additions.gui.GuiScrollingListAnyHeight;
import com.tmtravlr.additions.gui.view.components.IGuiViewComponent;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.GuiScrollingList;

/**
 * Scrolling list for all the View screens.
 * 
 * @author Tmtravlr (Rebeca Rey)
 * @since August 2017 
 */
public class GuiScrollingView extends GuiScrollingListAnyHeight {
	
	private GuiView parent;
	private ArrayList<IGuiViewComponent> components = new ArrayList<>();
	private int listSize = 0;

	public GuiScrollingView(GuiView parent) {
		super(parent.mc, parent.width, parent.height - 70, 30, 0, parent.width, parent.height);
		this.parent = parent;
	}

	@Override
	protected int getSize() {
		return components.size();
	}

	@Override
	protected int getSlotHeight(int slotId, int entryRight) {
		IGuiViewComponent component = this.components.get(slotId);
		return component == null ? 0 : component.getHeight(this.left, entryRight);
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {}

	@Override
	protected boolean isSelected(int index) {
		return false;
	}

	@Override
	protected void drawSlot(int slot, int right, int top, int buffer, Tessellator tess) {
		IGuiViewComponent component = components.get(slot);
		if (component != null) {
			int leftStart = this.left;
			int componentHeight = component.getHeight(this.left, right);
			
			if (component.getLabel() != null && !component.getLabel().isEmpty()) {
				this.parent.drawString(this.parent.getFontRenderer(), I18n.format(component.getLabel()), this.left + 10, top + componentHeight / 2 - 5, 0xFFFFFF);
				leftStart += GuiView.LABEL_OFFSET + 10;
			}
		
			if (component.isRequired()) {
				this.parent.drawString(this.parent.getFontRenderer(), "*", leftStart - 10, top + componentHeight / 2 - 5, 0xFFFFFF);
			}
		
		
			component.drawInList(leftStart, top, right, this.mouseX, this.mouseY);
		}
	}

	public void addComponent(IGuiViewComponent component) {
		this.components.add(component);
	}

	public void removeComponent(IGuiViewComponent component) {
		this.components.remove(component);
	}
	
	public void onMouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for (IGuiViewComponent component : components) {
			component.onMouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	public void onKeyTyped(char keyTyped, int keyCode) throws IOException {
		for (IGuiViewComponent component : components) {
			component.onKeyTyped(keyTyped, keyCode);
		}
	}
	
	@Override 
	public void handleMouseInput(int mouseX, int mouseY) throws IOException {
		boolean scrollEditArea = true;
		for (IGuiViewComponent component : components) {
			if (component.onHandleMouseInput(mouseX, mouseY)) {
				scrollEditArea = false;
			}
		}
		if (scrollEditArea) {
			super.handleMouseInput(mouseX, mouseY);
		}
	}
}