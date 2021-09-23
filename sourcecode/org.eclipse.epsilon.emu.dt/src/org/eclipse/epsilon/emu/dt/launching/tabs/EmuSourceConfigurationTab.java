/*******************************************************************************
 * Copyright (c) 2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Faisal Alhwikem
 ******************************************************************************/
package org.eclipse.epsilon.emu.dt.launching.tabs;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.AbstractSourceConfigurationTab;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emu.dt.EmuPlugin;
import org.eclipse.epsilon.emu.dt.launching.EmuLaunchConfigurationAttributes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EmuSourceConfigurationTab extends AbstractSourceConfigurationTab {

	// output directory
	protected Group group4OutputDir = null;
	protected Button button4OutputDirDefault = null;
	protected Button button4OutputDirCustom = null;
	protected Text text4OutputDirPath = null;
	protected Button button4BrowseButtonOutputDir;

	// file extension
	protected Group group4OutputExtension = null;
	protected Button button4FileExtensionDefault = null;
	protected Button button4FileExtensionCustom = null;
	protected Text text4FileExtension = null;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout());

		new EmuSourceConfigurationTab().createControl(shell);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		extras.setLayout(new GridLayout(1, true));
		// 1. Output Dir group
		group4OutputDir = createGroup(extras, "The directory of generated mutants should be: ", 1);

		button4OutputDirDefault = new Button(group4OutputDir, SWT.RADIO);
		button4OutputDirDefault.setText("Default directory (GenMutations)");
		// button4OutputDirDefault.addSelectionListener(this);

		button4OutputDirCustom = new Button(group4OutputDir, SWT.RADIO);
		button4OutputDirCustom.setText("The following directory: ");
		button4OutputDirCustom.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				updateEnabledStateOfOutputWidgets();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateEnabledStateOfOutputWidgets();

				if (button4OutputDirCustom.getSelection())
					text4OutputDirPath.setFocus();
			}
		});
		// output dir coomposite
		final Composite composite1 = createTwoColumnComposite(group4OutputDir);
		text4OutputDirPath = new Text(composite1, SWT.BORDER);
		final GridData data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalIndent = 25;
		text4OutputDirPath.setLayoutData(data1);
		text4OutputDirPath.addModifyListener(this);
		button4BrowseButtonOutputDir = createBrowseWorkspaceForContainerButton(composite1, text4OutputDirPath, "Output directory", "Choose a directory: ");

		// 2. Output file exteision group
		group4OutputExtension = createGroup(extras, "File output extension: ", 1);

		final Composite composite2 = new Composite(group4OutputExtension, SWT.NONE);
		composite2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite2.setLayout(new GridLayout(3, false));

		button4FileExtensionDefault = new Button(composite2, SWT.RADIO);
		button4FileExtensionDefault.setText("Default file extension (*.mutant)");

		button4FileExtensionCustom = new Button(composite2, SWT.RADIO);
		button4FileExtensionCustom.setText("This file extension: ");

		final GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
		text4FileExtension = new Text(composite2, SWT.BORDER);
		text4FileExtension.setTextLimit(10);
		text4FileExtension.setLayoutData(data2);
		text4FileExtension.addModifyListener(this);

		button4FileExtensionCustom.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				text4FileExtension.setEnabled(button4FileExtensionCustom.getSelection());
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				text4FileExtension.setEnabled(button4FileExtensionCustom.getSelection());

				if (button4FileExtensionCustom.getSelection())
					text4FileExtension.setFocus();
				else
					text4FileExtension.setText("");
			}
		});
		// extras.layout(true, true);
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);

		configuration.setAttribute(EmuLaunchConfigurationAttributes.EMU_GENERATE_TO, EmuLaunchConfigurationAttributes.TO_DEFAULT_OUTPUT_DIR);
		configuration.setAttribute(EmuLaunchConfigurationAttributes.OUTPUT_DIR_PATH, "");
		configuration.setAttribute(EmuLaunchConfigurationAttributes.EMU_SAVE_EXTENSION, EmuLaunchConfigurationAttributes.DEFAULT_FILE_EXTENSION);
		configuration.setAttribute(EmuLaunchConfigurationAttributes.OUTPUT_FILE_EXTENTION, ".mutant");
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);

		if (button4OutputDirDefault.getSelection())
			configuration.setAttribute(EmuLaunchConfigurationAttributes.OUTPUT_DIR_PATH, "");
		else
			configuration.setAttribute(EmuLaunchConfigurationAttributes.OUTPUT_DIR_PATH, text4OutputDirPath.getText());
		if (button4FileExtensionDefault.getSelection())
			configuration.setAttribute(EmuLaunchConfigurationAttributes.OUTPUT_FILE_EXTENTION, ".mutant");
		else
			configuration.setAttribute(EmuLaunchConfigurationAttributes.OUTPUT_FILE_EXTENTION, text4FileExtension.getText());
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {

		super.initializeFrom(configuration);
		try {
			// 1. mutation directory
			switch (configuration.getAttribute(EmuLaunchConfigurationAttributes.EMU_GENERATE_TO, EmuLaunchConfigurationAttributes.TO_DEFAULT_OUTPUT_DIR)) {
			case EmuLaunchConfigurationAttributes.TO_DEFAULT_OUTPUT_DIR:
				button4OutputDirDefault.setSelection(true);
				button4OutputDirCustom.setSelection(false);
				break;
			case EmuLaunchConfigurationAttributes.TO_CUSTOM_OUTPUT_DIR:
				button4OutputDirDefault.setSelection(false);
				button4OutputDirCustom.setSelection(true);
				break;
			}
			text4OutputDirPath.setText("");
			updateEnabledStateOfOutputWidgets();
			// 2. mutant extension
			switch (configuration.getAttribute(EmuLaunchConfigurationAttributes.EMU_SAVE_EXTENSION, EmuLaunchConfigurationAttributes.DEFAULT_FILE_EXTENSION)) {
			case EmuLaunchConfigurationAttributes.DEFAULT_FILE_EXTENSION:
				button4FileExtensionDefault.setSelection(true);
				button4FileExtensionCustom.setSelection(false);
				break;
			case EmuLaunchConfigurationAttributes.CUSTOM_FILE_EXTENSION:
				button4FileExtensionDefault.setSelection(false);
				button4FileExtensionCustom.setSelection(true);
				break;
			}
			text4FileExtension.setText("");
			text4FileExtension.setEnabled(button4FileExtensionCustom.getSelection());
		} catch (Exception ex) {
			LogUtil.log(ex);
		}
	}

	private void updateEnabledStateOfOutputWidgets() {
		text4OutputDirPath.setEnabled(button4OutputDirCustom.getSelection());
		button4BrowseButtonOutputDir.setEnabled(button4OutputDirCustom.getSelection());
	}

	@Override
	public EpsilonPlugin getPlugin() {
		return EmuPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/epl.gif";
	}

	@Override
	public String getFileExtension() {
		return "emu";
	}

	@Override
	public String getSelectionTitle() {
		return "Select an EMU Program";
	}

	@Override
	public String getSelectionSubtitle() {
		return "EMU Programs in Workspace";
	}

	public String getLaunchConfigurationKey() {
		return "SOURCE.EMU";
	}

}
