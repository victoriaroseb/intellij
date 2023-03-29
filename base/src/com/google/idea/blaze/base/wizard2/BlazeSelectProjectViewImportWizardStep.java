/*
 * Copyright 2016 The Bazel Authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.idea.blaze.base.wizard2;

import com.google.idea.blaze.base.wizard2.ui.BlazeAddOverrideFlagsControl;
import com.google.idea.blaze.base.wizard2.ui.BlazeSelectProjectViewControl;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.options.ConfigurationException;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JPanel;

class BlazeSelectProjectViewImportWizardStep extends ProjectImportWizardStep {

  private final JPanel component = new JPanel(new GridLayout(2,1));
  private BlazeSelectProjectViewControl control;
  private BlazeAddOverrideFlagsControl flagControl;
  private boolean settingsInitialised;

  public BlazeSelectProjectViewImportWizardStep(WizardContext context) {
    super(context);
  }

  @Override
  public JComponent getComponent() {
    return component;
  }

  @Override
  public void updateStep() {
    if (!settingsInitialised) {
      init();
    } else {
      control.update(getProjectBuilder());
    }
  }

  private void init() {
    control = new BlazeSelectProjectViewControl(getProjectBuilder());
    this.component.add(control.getUiComponent());
    flagControl = new BlazeAddOverrideFlagsControl(getProjectBuilder());
    this.component.add(flagControl.getUiComponent());
    settingsInitialised = true;
  }

  @Override
  public void validateAndUpdateModel() throws ConfigurationException {
    flagControl.validateAndUpdateModel(getProjectBuilder());
    control.validateAndUpdateModel(getProjectBuilder());
  }

  @Override
  public void onWizardFinished() throws CommitStepException {
    control.commit();
  }

  @Override
  public String getHelpId() {
    return "docs/project-views";
  }
}
