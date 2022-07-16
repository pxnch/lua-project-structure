# lua-project-structurizer
This Repository is about a small tool I wrote to structure my Lua Projects in an easy and convenient way

## Usage
Java 17 or newer is needed in order to work

### Generating a new Project
- Download the Latest Release or compile it yourself
- Run the output in the Folder that you want to use for your Lua Project
- Edit the generated build.prj file to change the Project Name
### Building the Project
- Run the JAR File in the Folder that your build.prj File is stored in and it will automatically detect all Files and try to append them to one File
## Building from Source
- Install Java JDK 17 or newer
- Install any IDE that can load Maven Projects and build the Module

## Syntax
- `begin_project <name>` - Sets the Project Name and is expected to be the first real Line of the build.prj
- `add_file <name>` - Adds a new File to the Project Structure
- All lines starting with a "#" will be handled as Comments and will be ignored when building the Project

