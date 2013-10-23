# Using GIT
 This is a guide for usage in the course TNM090, at Linköpings University for the usage of git witihin the project called "Robot Game"  
While this guide may have other usecases than the specified, it's primarily designed to follow the code styles and workflows of this course.
##GIT Setup
Use the following guides for setting up git

[https://help.github.com/articles/set-up-git](https://help.github.com/articles/set-up-git)  
[https://help.github.com/articles/generating-ssh-keys](https://help.github.com/articles/generating-ssh-keys)  
It is possible to use the Github native client for both Windows and Mac OS X, however, sideffects such as bad merge conflict handling can be an issue.  
[Windows](http://windows.github.com/), [Mac](http://mac.github.com/)
##Common Usecases
Almost all features and implementations should have their own branch. So before work starts at a new task, create a new, up to date branch by running the following commands while on the master branch.  

    git pull origin master 
    git checkout -b <feature>  
     
This creates a new branch named feature, from the latest revision of master.  

To add a file or create a file to the staging area use the ``git add`` command.  
Remove a file with the ``git rm`` command.  
In order to remove a file from the staging area, use the ``git rm --cached`` command  
Moving a file already in the repository can be done with ``git mv``  

When you are satisfied with the changes you've made, run the ``git commit -m "<Commit Message>" ``.  
After which, run ``git push origin <feature>`` to push it up to github.  
If you can now see your branch on [https://github.com/kallekrantz/RobotGame/branches](https://github.com/kallekrantz/RobotGame/branches), you're ready for the next step!

Go to the following link [https://github.com/kallekrantz/RobotGame/pulls](https://github.com/kallekrantz/RobotGame/pulls), click "New pull request"  
From here you can specify from which branch you want to create a pull request into a certain case.  
The originating branch is your own branch, IE ``<feature>``.   
The receiving branch should be master. 

Github now builds a pull request for you and shows you what files and the changes in them that are included.  
Check all files specified in the pull request to verify that everything looks correct.  
When this is done, click the "Create a pull request" button. From here you can specify a title for the pull request, name this appropriately for what feature is included.
In the Write area, you can specify people that are suited to review the pull request by using @username notation.

###Reviewer (Will be updated)
If you have reviewed the files specified in the pull request, you should be able to merge automatically. If not, use the following steps, complain to the originator, and tell them to run the following commands!

Run the following command in order to get a working copy of the files in the pull request in your working directory:
  
    git pull --all
    git checkout <pull request branch>

Afterwards, run 
``git merge master`, this will give you a specification of what files are conflicting. Git will create markings in each of these files in order to indicate where the conflict originated.  
Open these files in the editor of your choice, find the markings and see which parts of the versions you want to keep.  
When done with all files, check if the application can still run, and run 

    git commit -m  <commit message>
    git push origin 

If you now check the pull request page on Github, the reviewer should now be able to merge automatically using the web GUI.

##Command cheat sheet
###git status
###git add
###git rm
###git commit 
###git pull
###git fetch
###git push
###git checkout
###git branch
###git merge
####merge conflicts
##Issues cheat sheet
