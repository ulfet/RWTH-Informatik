/* @(#)ArrangeToolBar.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw.samples.svg.gui;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.draw.action.BringToFrontAction;
import org.jhotdraw.draw.action.SendToBackAction;
import org.jhotdraw.draw.event.SelectionComponentDisplayer;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.samples.svg.SVGLabels;
import org.jhotdraw.util.ResourceBundleUtil;

import org.jhotdraw.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * ArrangeToolBar.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class ArrangeToolBar extends AbstractToolBar {
    private static final long serialVersionUID = 1L;

    @Nullable private SelectionComponentDisplayer displayer;

    /** Creates new instance. */
    public ArrangeToolBar() {
        ResourceBundleUtil labels = SVGLabels.getLabels();
        setName(labels.getString(getID() + ".toolbar"));
    }

    @Override
    public void setEditor(DrawingEditor newValue) {
        if (displayer != null) {
            displayer.dispose();
            displayer = null;
        }
        super.setEditor(newValue);
        if (newValue != null) {
            displayer = new SelectionComponentDisplayer(editor, this);
            displayer.setVisibleIfCreationTool(false);
        }
    }

    @Override
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;

        switch (state) {
            case 1: {
                p = new JPanel();
                p.setOpaque(false);
                p.setBorder(new EmptyBorder(5, 5, 5, 8));

                // Abort if no editor is set
                if (editor == null) {
                    break;
                }

                ResourceBundleUtil labels = SVGLabels.getLabels();

                GridBagLayout layout = new GridBagLayout();
                p.setLayout(layout);

                GridBagConstraints gbc;
                AbstractButton btn;
                AbstractSelectedAction d;

                btn = new JButton(d = new BringToFrontAction(editor));
                disposables.add(d);
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                btn.setText(null);
                labels.configureToolBarButton(btn, BringToFrontAction.ID);
                btn.putClientProperty("hideActionText", Boolean.TRUE);
                gbc = new GridBagConstraints();
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.EAST;
                p.add(btn, gbc);


                btn = new JButton(d = new SendToBackAction(editor));
                disposables.add(d);
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                btn.setText(null);
                labels.configureToolBarButton(btn, SendToBackAction.ID);
                btn.putClientProperty("hideActionText", Boolean.TRUE);
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                gbc = new GridBagConstraints();
                gbc.gridy = 1;
                gbc.insets = new Insets(3, 0, 0, 0);
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.weighty = 1f;
                p.add(btn, gbc);
            }
            break;
        }
        return p;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected String getID() {
        return "arrange";
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
