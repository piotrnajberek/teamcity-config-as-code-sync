import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.12"

project {

    vcsRoot(HttpsGithubComPiotrnajberekAnsibleRefsHeadsMain)

    buildType(Build)

    params {
        password("password", "credentialsJSON:2bec5dbc-3af3-4835-835c-f62d9029c967", display = ParameterDisplay.HIDDEN, readOnly = true)
        param("username", "marian")
    }

    subProject(SubProject01)
    subProject(SubProject02)
    subProject(SubProject03)
}

object Build : BuildType({
    name = "Build"

    steps {
        script {
            name = "echo"
            id = "echo"
            scriptContent = """echo "hellow""""
        }
    }
})

object HttpsGithubComPiotrnajberekAnsibleRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/piotrnajberek/ansible#refs/heads/main"
    url = "https://github.com/piotrnajberek/ansible"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "piotrnajberek"
        password = "credentialsJSON:c29674f8-a043-48e9-93cd-526307de67ae"
    }
})


object SubProject01 : Project({
    name = "SubProject01"

    buildType(SubProject01_Build01)
})

object SubProject01_Build01 : BuildType({
    name = "build01"

    vcs {
        root(HttpsGithubComPiotrnajberekAnsibleRefsHeadsMain)
    }

    steps {
        script {
            name = "Step01"
            id = "Step01"
            scriptContent = """echo "xxx""""
        }
        script {
            name = "Step02_ping"
            id = "Step02"
            scriptContent = "ansible-playbook -i ping/inventory/prod/hosts ping/playbooks/ping_icmp.yml"
        }
    }
})


object SubProject02 : Project({
    name = "SubProject02"

    buildType(SubProject02_Build01)
})

object SubProject02_Build01 : BuildType({
    name = "build01"

    steps {
        script {
            name = "Step01"
            id = "Step01"
            scriptContent = """echo "xxx""""
        }
        script {
            name = "Step02"
            id = "Step02"
            scriptContent = """echo "xxx2222""""
        }
    }
})


object SubProject03 : Project({
    name = "SubProject03"
})
