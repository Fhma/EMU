package org.eclipse.epsilon.emu.emf.dt;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emf.dt.NewEmfModelWizard;
import org.eclipse.epsilon.emu.emf.EmfModelEMU;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.Model;
import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;

public class EMUNewEmfModelWizard extends NewEmfModelWizard {

	@Override
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String fileName = page.getFileName();
		final String rootClass = page.getRootClass();
		final String metamodelUri = page.getMetaModelUri();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, metamodelUri, rootClass, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	private void doFinish(String containerName, String fileName, String metamodelUri, String rootClass,
			IProgressMonitor monitor) throws CoreException {

		EmfModelEMU model = new EmfModelEMU();

		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_MODEL_URI,
				URI.createPlatformResourceURI(containerName + "/" + fileName, true));
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodelUri);
		properties.put(Model.PROPERTY_READONLOAD, false);

		try {
			model.load(properties);
		} catch (EolModelLoadingException e) {
			LogUtil.log(e);
		}

		try {
			model.createInstance(rootClass);
		} catch (Exception ex) {
			LogUtil.log(ex);
		}

		model.store();
		model.dispose();

		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(containerName + "/" + fileName));
		// Open the new model for editing
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
					LogUtil.log(e);
				}
			}
		});
	}
}
