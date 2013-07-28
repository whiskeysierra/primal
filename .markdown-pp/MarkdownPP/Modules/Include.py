# Copyright (C) 2010 John Reese
# Licensed under the MIT license

import re
from os import path

from MarkdownPP.Module import Module
from MarkdownPP.Transform import Transform

includere = re.compile("^!INCLUDE\s+(?:\"([^\"]+)\"|'([^']+)')\s*$")

class Include(Module):
	"""
	Module for recursively including the contents of other files into the current
	document using a command like `!INCLUDE "path/to/filename"`.  Target paths can
	be absolute or relative to the file containing the command.
	"""

	# includes should happen before anything else
	priority = 0

	def transform(self, data):
		transforms = []

		linenum = 0
		for line in data:
			match = includere.search(line)
			if match:
				includedata = self.include(match)

				transform = Transform(linenum=linenum, oper="swap", data=includedata)
				transforms.append(transform)

			linenum += 1

		return transforms

	def include(self, match, pwd=""):
		if match.group(1) is None:
			filename = match.group(2)
		else:
			filename = match.group(1)

		if not path.isabs(filename):
			filename = path.join(pwd, filename)

		if path.isfile(filename):
			f = open(filename, "r")
			data = f.readlines()
			f.close()

			# recursively include file data
			linenum = 0
			for line in data:
				match = includere.search(line)
				if match:
					data[linenum:linenum+1] = self.include(match, path.dirname(filename))

				linenum += 1

			return data

		return []
