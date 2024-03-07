import io


class LngEditor:
    def __init__(self):
        self.header = 0
        self.langStrings = []
        self.stringsCount = 0

    def read_file(self, input_stream):
        lang_file_data_stream = io.BytesIO(input_stream.read())

        self.header = self._read(lang_file_data_stream)
        read = lang_file_data_stream.read(1)[0]

        if read > 2:
            lang_file_data_stream.close()
            raise ValueError("wrong lng file")

        a2 = self._read(lang_file_data_stream)
        self.stringsCount = a2
        iArr = [0] * (self.stringsCount + 1)
        self.langStrings = []

        for i in range(len(iArr)):
            iArr[i] = self._read(lang_file_data_stream)

        for index in range(len(iArr) - 1):
            string_size = iArr[index + 1] - iArr[index]
            string_byte = lang_file_data_stream.read(string_size)
            lang_string = string_byte.decode("utf-8", "replace")
            self.langStrings.append(lang_string)

        lang_file_data_stream.close()

    def write_file(self, output_stream, lang_strings):
        f2a = io.BytesIO()
        byte_array_output_stream = io.BytesIO()
        length = len(lang_strings)
        iArr = [0] * (length + 1)
        iArr[0] = ((length + 1) << 1) + 3
        i = 0

        for i2 in range(length):
            bytes_str = lang_strings[i2].encode("utf-8")
            i += len(bytes_str)
            iArr[i2 + 1] = iArr[i2] + len(bytes_str)
            byte_array_output_stream.write(bytes_str)

        byte_array_output_stream.seek(0)
        i3 = iArr[0] + 2 + i
        self.header = i3
        self._write(i3, f2a)
        f2a.write(bytes([2]))
        self._write(length, f2a)

        for i4 in iArr:
            self._write(i4, f2a)

        f2a.write(byte_array_output_stream.read())
        f2a.seek(0)
        output_stream.write(f2a.read())

    def _write(self, i, data_output_stream):
        data_output_stream.write(bytes([i & 255]))
        data_output_stream.write(bytes([(i >> 8) & 255]))

    def _read(self, data_input_stream):
        return int.from_bytes(data_input_stream.read(2), byteorder="little")

    def get_lang_strings(self):
        return self.langStrings
