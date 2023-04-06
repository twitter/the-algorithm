# n64graphics_ci

Allows you to convert PNG image files to/from N64 CI format. This is temporary until queueRAM adds CI support into his official n64graphics tool. This tool does not process RGBA, IA, or I textures, use n64graphics for those.

CI4 textures will always assume a 16 color palette is used, and CI8 textures a 256 palette is used. The palette will be generated as a seperate file. The palette file will be named after the CI filename, but postpended with `.pal`.

## Libraries Used (All MIT licensed)

* **Exoquant** by Dennis Ranke, for color reduction. https://github.com/exoticorn/exoquant 
* **stb** by Sean Barrett, for loading PNG images. https://github.com/nothings/stb

## PNG -> CI4 + Palette

`./n64graphics_ci -i image.ci4 -g image.png -f ci4`

## CI4 + Palette -> 32x32 PNG

`./n64graphics_ci -e image.ci4 -g image.ci4.png -f ci4 -w 32 -h 32`

## PNG -> CI8 + Palette

`./n64graphics_ci -i image.ci8 -g image.png -f ci8`

## CI8 + Palette -> 32x32 PNG

`./n64graphics_ci -e image.ci8 -g image.ci8.png -f ci8 -w 32 -h 32`

## Comparision
![alt text](https://i.imgur.com/r3PhZp0.png)
