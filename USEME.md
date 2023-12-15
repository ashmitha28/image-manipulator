<h3>How to run the program</h3>
<ul>
<p>The program is run using the ProgramRunner class. This class can be run without any arguments which once run,
will bring up the GUI which the user can use to load and perform operations on images. Running the JAR file as <b>java -jar image-manipulator.jar</b>  at res/ folder will bring up the UI as well.</p>
<p>When passed '-text' as arguments, ProgramRunner will run the program and the user will be prompted to input any of the valid command for the operations on images.
To run the example script provided in this way, once the program runner is running, input "run res/script.txt" in the command line and after the execution of the script, type "exit" to stop the program.
Running the JAR file as <b>java -jar image-manipulator.jar -text</b>  at res/ folder will bring up the CLI for command input as well.</p>
<p>The class can also be provided a command line argument specifying the script file that contains the commands to be run. For that use the command line arguments '-file <i>filename</i>'. To run the example script provided this way,
run the ProgramRunner class with '-file res/script.txt' as command line argument.
<p>To run the program making use of a given script file using the JAR file provided, run 
<b>java -jar image-manipulator.jar -file script-jar.txt</b> after <i>cd</i>-ing into the res/ folder in the project.
Ensure that the images and results folders are present in res root along with the jar as the operations are performed
using the images present in the 'images' folder, and saved into 'results' folder.
</p></ul>

<h3>Performing Operations on the GUI</h3>
<li>Click on the load button and select an image (choosing any file that are not supported will display an error message).
Go into images folder within the res folder(where the jar is present) for example images. </li>
<li>Click on the save button to save the active image (trying to save an image without even loading one will display an error message - so will trying to save an
in an image format not supported. Use only jpg,png or ppm formats to save ).</li>
<li>To perform any operation, say 'Visualize Red', on the active image, click on the 'Visualize Red' button and then click on the 'Apply Filter' to see the applied operation. The histogram image will also get updated upon clicking the Apply Filter button.
<p>The same thing can be done for applying all other operations supported in the GUI which currently are: Visualize Red, Visualize Green, Visualize Blue, Blur, Compress, Color Correct, Sharpen, Luma Greyscale, Sepia, Flip Horizontally and Flip Vertically.</p></li>
<li>Some operations support preview operation. For example, upon clicking the blur button, a preview button appears, which can be 
used to quickly see what the image will look like after applying the operation. When the preview button is clicked, an input dialog pops up prompting for an integer input (which will retry until a valid integer between 0-100 is entered or the dialog box is cancelled).
After inputting a value for the preview operation, it can be noticed that the visible image has now changed to a partially operated image that depends on the input provided to the dialog box.
There then appears a toggle button which can be used to toggle between the split view image and the original image. To modify where the split occurs, simply select the preview button again and input the preview percent accordingly.
<p>The operations that support the preview operation are: Blur, Sharpen, Levels Adjust, Color Correct, Luma Greyscale, Sepia</p></li>
<li>Compression operation requires to know by how much should the image be compressed, and so in addition to all that has been briefed above, an input box comes up
prompting the user to enter a valid compression percentage.
<p>Levels Adjust similarly requires the b, m , and w values to fit a curve that will be used for adjusting the 
levels of the image. Three input dialog boxes comes up specifying the input constraints, which will keep coming up until a valid integer is given(or if the user cancels the operation by clicking on the Cancel button of the input dialog).</p>
</li>

<h3>Supported commands (For script and CLI)</h3>

<ul>
<b>load image-path image-name</b>: Load an image from the specified path and refer it to henceforth in the program by the given image name(image names should not contain any spaces).
<p><i>Example</i>: load resources/bird.jpg bird</p>
<p><i>Conditions</i>:
<ul><li>The image format must be supported by the application. Supported formats are currently jpg, png and ppm</li></ul></p>

<b>save image-path image-name</b>: Save the image with the given name to the specified path which
should include the name of the file(image names should not contain any spaces).
<p><i>Example</i>: save resources/bird.jpg bird</p>
<p><i>Conditions</i>: 
<ul>
<li>The image to be saved must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
<li>The image format must be supported by the application. Supported formats are currently jpg, png and ppm</li>
</ul></p>

<b>red-component image-name dest-image-name</b>: Create an image with the red-component of the image
with the given name, and refer to it henceforth in the program by the given destination name.
<p><i>Example</i>: red-component bird bird-red</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>green-component image-name dest-image-name</b>: Create an image with the green-component of the image
with the given name, and refer to it henceforth in the program by the given destination name.
<p><i>Example</i>: green-component bird bird-green</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>blue-component image-name dest-image-name</b>: Create an image with the blue-component of the image
with the given name, and refer to it henceforth in the program by the given destination name.
<p><i>Example</i>: blue-component bird bird-blue</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>value-component image-name dest-image-name</b>: Create an image with the value-component of the image
with the given name, and refer to it henceforth in the program by the given destination name. This will be 
a greyscale image.
<p><i>Example</i>: value-component bird bird-value</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>luma-component image-name dest-image-name</b>: Create an image with the luma-component of the image
with the given name, and refer to it henceforth in the program by the given destination name. This will be
a greyscale image.
<p><i>Example</i>: luma-component bird bird-luma</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>intensity-component image-name dest-image-name</b>: Create an image with the intensity-component of the image
with the given name, and refer to it henceforth in the program by the given destination name. This will be
a greyscale image.
<p><i>Example</i>: intensity-component bird bird-intensity</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>horizontal-flip image-name dest-image-name</b>: Flip an image horizontally to create a new image,
referred to henceforth by the given destination name.
<p><i>Example</i>: horizontal-flip bird bird-horizontal-flip</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>vertical-flip image-name dest-image-name</b>: Flip an image vertically to create a new image,
referred to henceforth by the given destination name.
<p><i>Example</i>: vertical-flip bird bird-vertical-flip</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>brighten increment image-name dest-image-name</b>: brighten the image by the given increment to
create a new image, referred to henceforth by the given destination name. The increment may be
positive (brightening) or negative (darkening).
<p><i>Example</i>: brighten 20 bird bird-brighter</p>
<p><i>Example 2</i>: brighten -20 bird bird-darker</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue</b>: split
the given image into three images containing its red, green and blue components respectively. These
would be the same images that would be individually produced with the red-component, green-component
and blue-component commands.
<p><i>Example</i>: rgb-split bird bird-red bird-green bird-blue</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>rgb-combine image-name red-image green-image blue-image</b>: Combine the three greyscale images
into a single image that gets its red, green and blue components from the three images respectively.
<p><i>Example</i>: rgb-combine bird bird-red bird-green bird-blue</p>
<p><i>Conditions</i>: 
<ul>
<li>The source images(note that there are three here, namely bird-red, bird-green, and bird-blue) must be present in the application, which implies the 
presence of at least three commands(of which at least one is load) which creates the source images for this command.</li>
</ul></p>

<b>blur image-name dest-image-name</b>: blur the given image and store the result in another image
with the given name.
<p><i>Example</i>: blur bird bird-blur</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>sharpen image-name dest-image-name</b>: sharpen the given image and store the result in another
image with the given name.
<p><i>Example</i>: sharpen bird bird-sharp</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>sepia image-name dest-image-name</b>: produce a sepia-toned version of the given image and store
the result in another image with the given name.
<p><i>Example</i>: sepia bird bird-sepia</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>compress percentage image-name dest-image-name</b>: Create a compressed version of an image.
Percentages between 0 and 100 are considered valid.
<p><i>Example</i>: compress 10 bird bird-compressed</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
<li>The compression value is a percentage and should be between 0 and 100 (both inclusive)</li>
</ul></p>

<b>histogram image-name dest-image-name</b>: Produce an image that represents the histogram of the
given image. The size of this image would be 256x256. It contains the histograms for the red, green
and blue channels as line graphs.
<p><i>Example</i>: histogram bird bird-histogram</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>color-correct image-name dest-image-name</b>: Support the ability to color-correct an image by
aligning the meaningful peaks of its histogram.
<p><i>Example</i>: color-correct bird bird-color-correct</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
</ul></p>

<b>levels-adjust b m w image-name dest-image-name</b>: This command can be used to adjust levels of
an image where b, m and w are the three relevant black, mid and white values respectively. These
values should be ascending in that order, and should be within 0 and 255 for this command to work
correctly.
<p><i>Example</i>: levels-adjust 12 140 244 bird bird-level-adjust</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
<li>The b, m, and w values should be ascending in that order, and should be within 0 and 255</li>
</ul></p>

<b>run script-file</b>: Load and run the script commands in the specified file.
<p><i>Example</i>: run example_script.txt</p>
<p><i>Conditions</i>: 
<ul>
<li>The source file should be present in the File System at the specified location.</li>
</ul></p>

<b>exit</b>: Exit the execution of the program.
<p><i>Example</i>: exit</p>

<h3>Note:</h3>
Some of the commands above support the ability to specify a vertical line to generate a split view
of operations. The operations that support this are blur, sharpen, sepia, greyscale(all three), color
correction and levels adjustment. The script commands for these operations accommodates an optional
parameter for the placement of the splitting line. For example, blur can be done by "blur image-name
dest-image-name" or "blur image-name dest-image split p" in that order where 'p' is a percentage of
the width (e.g. 50 means place the line halfway through the width of the image). The output image
would contain only the relevant part suitably transformed, with the original image in the remaining
part.
<p><i>Example</i>: color-correct bird bird-color-correct split 20</p>
<p><i>Conditions</i>: 
<ul>
<li>The source image must be present in the application, which implies the 
presence of at least one load command (and usually other commands as well) before this command.</li>
<li>The split percentage should be between 0 and 100 (inclusive).</li>
</ul></p>
</ul>