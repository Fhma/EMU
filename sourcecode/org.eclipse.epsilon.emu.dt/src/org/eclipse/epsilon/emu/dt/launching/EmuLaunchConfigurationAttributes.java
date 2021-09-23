/*********************************************************************
* Copyright (c) 2018 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* Contributors:
*     Faisal Alhwikem
**********************************************************************/
package org.eclipse.epsilon.emu.dt.launching;

public class EmuLaunchConfigurationAttributes {

	public static final String EMU_GENERATE_TO = "emuGenerateTo";
	public static final String OUTPUT_DIR_PATH = "outputFolder";

	public static final int TO_DEFAULT_OUTPUT_DIR = 1;
	public static final int TO_CUSTOM_OUTPUT_DIR = 2;

	public static final String EMU_SAVE_EXTENSION = "emuFileExtension";
	public static final String OUTPUT_FILE_EXTENTION = "outputFileExtension";
	public static final int DEFAULT_FILE_EXTENSION = 3;
	public static final int CUSTOM_FILE_EXTENSION = 4;
}
