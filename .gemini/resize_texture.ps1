Add-Type -AssemblyName System.Drawing
$srcPath = 'c:\Users\Gulab\IdeaProjects\mob-parts-template-26.1\fabric\src\main\resources\assets\mob_parts\textures\item\zombie_hand.png'
$img = [System.Drawing.Image]::FromFile($srcPath)
$newImg = New-Object System.Drawing.Bitmap(16, 16)
$g = [System.Drawing.Graphics]::FromImage($newImg)
$g.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::NearestNeighbor
$g.DrawImage($img, 0, 0, 16, 16)
$g.Dispose()
$img.Dispose()
$newImg.Save($srcPath, [System.Drawing.Imaging.ImageFormat]::Png)
$newImg.Dispose()
Write-Host "Resized to 16x16"
