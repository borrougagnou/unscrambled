from PIL import Image

def unscramble_img(img_scrambled, dst_img_path):
    myImg = Image.open(img_scrambled)
    result = Image.new("RGB", myImg.size)

    # Initialisation
    maxH = myImg.height
    maxW = myImg.width
    y = 0
    x = 0

    while (y < maxH - 200):
        # cut the picture to take only the line
        # (begin left, begin upper, end right, end lower) coordinate
        row1 = myImg.crop( (0, y,       maxW, y + 100) ) # take first line
        row2 = myImg.crop( (0, y + 100, maxW, y + 200) ) # take second line

        # switch time !
        result.paste(row2, (0, y))
        result.paste(row1, (0, y + 100))
        y += 200

    if (y > maxH - 200):
        row = myImg.crop((0, y, maxW, maxH))
        result.paste(row, (0, y))

    while (x < maxW - 200):
        col1 = result.crop((x      , 0, x + 100, maxH))
        col2 = result.crop((x + 100, 0, x + 200, maxH))

        # switch time !
        result.paste(col2, (x, 0))
        result.paste(col1, (x + 100, 0))
        x += 200

    result.save(dst_img_path)


### TEST MODE
def test_unscramble():
        unscramble_img("01.jpg", "01_OK.jpg");
        unscramble_img("01-2.jpg", "01-2_OK.jpg");
        unscramble_img("02.jpg", "02_OK.jpg");
test_unscramble()
