package net.simon;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

    public static void main( String[] args ) throws IOException {
        File file = new File( "build.prj" );
        if ( !file.exists( ) ) {
            file.createNewFile( );

            new File( "entry.lua" ).createNewFile( );
            new File( "end.lua" ).createNewFile( );

            BufferedWriter writer = new BufferedWriter( new FileWriter( file ) );
            writer.write( "begin_project project_name\n" );
            writer.write( "\n" );
            writer.write( "# this file will be the first that gets included and is your place to register general global variables or generic functions to call on every other script \n" );
            writer.write( "add_file entry.lua\n" );
            writer.write( "\n" );
            writer.write( "# put your project files below in the same order as they should be mashed together!\n" );
            writer.write( "# add_file path/to/code.lua\n" );
            writer.write( "\n" );
            writer.write( "# this file will be the last that gets included and is meant for things like calling functions from the other files in general, as here everything will be initialized and \"loaded\"\n" );
            writer.write( "add_file end.lua" );

            writer.flush( );
            writer.close( );

            System.out.println( "Created new Project Build File, please Edit it to match your Project!" );
        } else {
            System.out.println( "Parsing build.prj, please wait a Moment..." );

            BufferedReader reader = new BufferedReader( new FileReader( file ) );
            String line = reader.readLine( );

            if ( line != null )
                while ( line != null && line.startsWith( "#" ) )
                    line = reader.readLine( );

            if ( line == null || !line.startsWith( "begin_project" ) ) {
                if ( line != null )
                    System.out.println( "Invalid Project Structure, expected \"begin_project\" but got \"" + line + "\"" );
                else
                    System.out.println( "Invalid File" );

                return;
            }

            String projectName = line.replace( "begin_project ", "" );
            System.out.println( "Building Project \"" + projectName + "\"" );

            long currentTime = System.currentTimeMillis( );

            ArrayList< String > fileNames = new ArrayList<>( );

            while ( ( line = reader.readLine( ) ) != null ) {
                if ( line.startsWith( "add_file" ) ) {
                    String fileToAdd = line.replace( "add_file ", "" );
                    fileNames.add( fileToAdd );

                    System.out.println( "Registered File \"" + fileToAdd + "\"" );
                } else if ( !line.startsWith( "#" ) && !line.startsWith( "\n" ) ) {
                    System.out.println( "Invalid Line beginning, expected \"add_file\" or \"#\" as beginning, but got \"" + line + "\"" );
                }
            }

            reader.close( );

            String finalFileName = projectName + ".lua";
            File resultFile = new File( finalFileName );
            if ( resultFile.exists( ) )
                resultFile.delete( );

            System.out.println( "Registered " + fileNames.size( ) + " Files, building \"" + finalFileName + "\" now" );

            resultFile.createNewFile( );

            BufferedWriter writer = new BufferedWriter( new FileWriter( resultFile ) );

            writer.write( "-- This File got automatically built from the Project \"" + projectName + "\" \n" );

            for ( String fileName : fileNames ) {
                File tempFile = new File( fileName );
                if ( !tempFile.exists( ) ) {
                    System.out.println( "Skipping invalid File \"" + fileName + "\"" );
                    continue;
                }

                writer.write( Files.readString( Path.of( fileName ) ) + "\n" );
            }

            writer.flush( );
            writer.close( );

            System.out.println( "Built successfully in " + ( System.currentTimeMillis( ) - currentTime ) + "ms" );
        }
    }

}
