import static liveplugin.PluginUtil.*


import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

import static liveplugin.PluginUtil.*

def docRelativePath = "doc"

def reqDocCorrelator = ["@Test-N320":"behavior.md"]


registerAction("Find Req file", "ctrl alt shift G") { AnActionEvent event ->
    def project = event?.project
    def editor = CommonDataKeys.EDITOR.getData(event.dataContext)
    if (project == null || editor == null) return

    //Check if the file is a Cucumber file
    if (editor.getVirtualFile().fileType.name !== "Cucumber"){
        show("not a cucumber file")
        return
    }

    //Take the PSI element under the caret
    int offset = editor.getCaretModel().getOffset();
    def psiFile = CommonDataKeys.PSI_FILE.getData(event.dataContext)
    def selectedTag = psiFile.findElementAt(offset).text

    //Open the Req file if founded
    if (reqDocCorrelator.containsKey(selectedTag)) {
        openInEditor(project.basePath + "/" + docRelativePath+ "/" + reqDocCorrelator.get(selectedTag),project)
y    }
    else {
        show("no correlation found")
    }

}
if (!isIdeStartup) show("Loaded 'Test Req plugin' action<br/>Use 'Ctrl+Alt+Shift+G' to run it")
