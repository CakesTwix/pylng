# Simple .lng File Parser

This is a simple .lng file parser that can parse .lng files and output them in json format for translate the content.

## More info

The .lng format is used in games from the German company HandyGames to store localized text and interface strings. This code was taken from a decompiled [Java application](https://seclub.org/dn.php?d=5&r=3&c=21&fd=8312).

## Usage

### Installing Dependencies

```bash
pip install deepl-cli
```

### Arguments

- `input_file`: Path to the .lng/json file that you want to parse.
- `--output_file OUTPUT_FILE`: Name of the output file (default: out.lng/out.json).
- `--convert_to_json`: Flag to convert the file to json/po format.
- `--translate`: Flag to translate the json content.

## Examples

1. Parse a .lng file to json format:

```bash
python -m pylng example.lng --convert_to_json
```

2. Translate the content of a json file:

```bash
python -m pylng example.json --translate
```

3. Convert .json to .lng:

```bash
python -m pylng example.json
```