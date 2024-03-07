import argparse
from pylng.lng import LngEditor
import json
from deepl import DeepLCLI
import time


def to_json(arr, filename):
    result = {}
    for item in arr:
        key = len(result)
        result[key] = item

    with open(filename, "w") as file:
        json.dump(result, file, ensure_ascii=False, indent=4)

    return json.dumps(result, ensure_ascii=False, indent=4)


parser = argparse.ArgumentParser(description="Simple .lng file parser")
parser.add_argument("input_file", help="Path to .lng/json file")
parser.add_argument(
    "--output_file",
    help="Name of the output file (default: out.lng/out.json)",
    default="out.lng",
)
parser.add_argument(
    "--convert_to_json", help="Flag to convert to json/po format", action="store_true"
)

parser.add_argument("--translate", help="Flag to translate json")

args = parser.parse_args()
input_file = args.input_file
output_file = args.output_file
convert_to_json = args.convert_to_json
translate = args.translate

editor = LngEditor()

# Check input file
if input_file.split(".")[1] == "lng":
    with open(input_file, "rb") as input_file:
        editor.read_file(input_file)

elif input_file.split(".")[1] == "json":
    lng_strings = []
    with open(
        input_file,
        "r",
    ) as file_in:
        data = json.load(file_in)
        lng_strings = list(data.values())
        print(lng_strings)

    with open(output_file, "wb") as file_out:
        editor.write_file(file_out, lng_strings)

# Need convert lng file to json for translate
if convert_to_json:
    to_json(editor.get_lang_strings(), output_file.replace("lng", "json"))
    exit(0)

# Translate json via Deepl
if translate:
    deepl = DeepLCLI("auto", translate)
    with open(
        input_file,
        "r",
    ) as file_in:
        data = json.load(file_in)
        for item in data:
            translated_item = deepl.translate(data[item])
            data[item] = translated_item
            with open(output_file.replace("lng", "json"), "w") as file_out:
                json.dump(data, file_out, ensure_ascii=False, indent=4)
