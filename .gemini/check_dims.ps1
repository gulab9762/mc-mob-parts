Add-Type -AssemblyName System.Drawing
$img = [System.Drawing.Image]::FromFile('c:\Users\Gulab\IdeaProjects\mob-parts-template-26.1\fabric\src\main\resources\assets\mob_parts\textures\item\zombie_hand.png')
Write-Host "Width: $($img.Width)"
Write-Host "Height: $($img.Height)"
$img.Dispose()
