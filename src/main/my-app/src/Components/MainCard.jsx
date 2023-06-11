import {
  Card,
  CardHeader,
  CardFooter,
  Image,
  Heading,
  Text,
  Button,
  Flex,
  Box,
} from '@chakra-ui/react';

import { ChatIcon, ExternalLinkIcon } from '@chakra-ui/icons';
import {useNavigate} from "react-router-dom";
import React from "react";

export const MainCard = ({ ListTitle, post}) => {
    const navigate = useNavigate();
    const btnRef = React.useRef();

    const  handleCardClick = () => {
        navigate(`/post/${post.id}`);
    };
  return (
      <Card maxW="md" style={{cursor:'pointer'}} onClick={handleCardClick} ref={btnRef}>
      <CardHeader>
        <Flex spacing="4">
          <Flex flex="1" gap="4" alignItems="center" flexWrap="wrap">
            <Box>
              <Heading size="lg">{ListTitle}</Heading>
              <Text>{"<"+post?.contentTitle+">"}</Text>
            </Box>
          </Flex>
        </Flex>
      </CardHeader>
      <Image
        objectFit="cover"
        src={post?.thumbnail}
        alt="Chakra UI"
      />

      <CardFooter
        justify="space-between"
        flexWrap="wrap"
        sx={{
          '& > button': {
            minW: '136px',
          },
        }}
      >
      </CardFooter>
    </Card>
  );
};
